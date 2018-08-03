package main;

import entidades.*;
import freemarker.template.Configuration;
import logical.*;
import servicios.Encriptamiento;
import servicios.JsonTransformer;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static spark.Spark.*;

public class RutasSpark {
    public void iniciarSpark() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
        cfg.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(cfg);
        File fotosDir = new File("photos");
        fotosDir.mkdir();

        List<Usuario> misUsuarios = new ArrayList<Usuario>();

        get("/", (request, response) -> {
            Usuario logUser = request.session(true).attribute("usuario");
            if(logUser==null)
                response.redirect("/login");
            else
                response.redirect("redSocial/userArea/" + logUser.getCorreo() + "/perfilUsuario");
            return "";
        });

        get("/login", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("titulo","Login");
            attributes.put("paises",ServiciosPais.getInstancia().findAllOrdenado());
            return new ModelAndView(attributes, "sign-in.html");
        }, freeMarkerEngine);

        post("/procesarUsuario", (request, response) -> {
            try {
                String correoAVerificar = request.queryParams("email");
                String passwordsAVerificar = request.queryParams("password");
                String isRecordado = request.queryParams("recordar");
                Usuario logUser = ServiciosUsuario.getInstancia().findByEmailAndPassword(correoAVerificar,passwordsAVerificar);

                if (logUser != null) {
                    request.session(true);
                    request.session().attribute("usuario", logUser);
                    if(isRecordado!=null){
                        response.cookie("/", "sr5h464h846s4dhds4h6w9uyh",
                                new Encriptamiento().encriptar(correoAVerificar), (60*60*24*7), false, true);
                    }
                    response.redirect("redSocial/userArea/" + logUser.getCorreo() + "/perfilUsuario");
                } else {
                    response.redirect("/iniciarSesion");
                }

            } catch (Exception e) {
                System.out.println("Error al intentar iniciar sesión " + e.toString());
                response.redirect("/login");
            }
            return "";
        });

        get("redSocial/userArea/:correo/perfilUsuario", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario logUser = request.session(true).attribute("usuario");
            String correoUser = request.params("correo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            attributes.put("usuario",logUser);
            attributes.put("posts",ServiciosPost.getInstancia().findByAuthor(logUser));
            attributes.put("fecha_nacimiento", user.getFechaNacimiento());
            attributes.put("pais_origen", user.getPais().getPais());
            attributes.put("ciudad_origen", user.getCiudad());
            attributes.put("lugar_estudio", user.getLugarDeEstudio());
            attributes.put("trabajo", user.getEmpleo());
            attributes.put("albumes", ServiciosAlbum.getInstancia().findAlbumsByUser(logUser));
            for (Album album : ServiciosAlbum.getInstancia().findAlbumsByUser(logUser)){
                System.out.println("Album cargado: " + String.valueOf(album.getIdAlbum()));
            }
            for (Post post: ServiciosPost.getInstancia().findByAuthor(logUser)){
                System.out.println(post.getCuerpo());
            }
            return new ModelAndView(attributes, "my-profile-feed.html");
        }, freeMarkerEngine);

        post("/registrarUsuario", (request, response) -> {
            try {
                String nombres = request.queryParams("nombres");
                String apellidos = request.queryParams("apellidos");
                String sexo = request.queryParams("cbMasculino");
                System.out.println(sexo);
                String fechaNacimiento = request.queryParams("fechaNacimiento");
                String pais = request.queryParams("cbBoxPais");
                String ciudad = request.queryParams("ciudad");
                String lugarEstudio = request.queryParams("lugarEstudio");
                String empleo = request.queryParams("empleo");
                String correo = request.queryParams("email");
                String password = request.queryParams("password");

                Usuario nuevoUsuario = new Usuario(nombres,apellidos,correo,password,
                        (sexo != null) ? "Masculino":"Femenino",
                        new SimpleDateFormat("yyyy-mm-dd").parse(fechaNacimiento),ServiciosPais.getInstancia()
                        .findByCountry(pais), ciudad,lugarEstudio,empleo,null,false);
                ServiciosUsuario.getInstancia().crear(nuevoUsuario);
                response.redirect("/login");

            } catch (Exception e) {
                System.out.println("Error al intentar registrar usuario" + e.toString());
            }
            return "";
        });

        post("/publicarPost", (request, response) -> {
            try {
                String imagenRuta = (ServiciosImagen.getInstancia().guardarFoto("imagen",fotosDir,request));
                Imagen imagen = null;
                System.out.println(request.queryParams("imagen"));
                if(imagenRuta != null){
                    System.out.println("Entró para guardar la imagen");
                    imagen = new Imagen(imagenRuta,null,null);
                }
                Usuario logUser = request.session(true).attribute("usuario");
                String cuerpo = request.queryParams("cuerpo");
                String tags = request.queryParams("tags");
                Set<Tag> postEtiquetas = Tag.crearEtiquetas(tags.split(","));
                Post nuevoPost = new Post(logUser,imagen,cuerpo,new Date(),null,postEtiquetas,null);
                ServiciosPost.getInstancia().crear(nuevoPost);
                response.redirect("/redSocial/userArea/" + logUser.getCorreo() + "/perfilUsuario");
            } catch (Exception e) {
                System.out.println("Error al realizar post" + e.toString());
            }
            return "";
        });

        post("/comentarPost/:id", (request, response) -> {
            try {
                String comentario = request.queryParams("comentarioNuevo");
                Usuario autor = request.session(true).attribute("usuario");
                Post postActual = ServiciosPost.getInstancia().find(Long.parseLong(request.params("id")));

                ComentarioPost nuevoComentario = new ComentarioPost(comentario,new Date(),autor,postActual);
                ServiciosComentarioPost.getInstancia().crear(nuevoComentario);
                response.redirect("/redSocial/userArea/" + autor.getCorreo() + "/perfilUsuario");

            } catch (Exception e) {
                System.out.println("Error al publicar comentario: " + e.toString());
            }
            return "";
        });

        get("/procesarLike/:id", (request, response) -> {
            try {
                    Usuario usuario = request.session(true).attribute("usuario");
                    String postId = request.params("id");

                    if(usuario != null){
                        Post post = ServiciosPost.getInstancia().find(Long.parseLong(postId));
                        if(ServiciosPost.getInstancia().findUserLike(usuario,post)){
                            ServiciosLikePost.getInstancia().deleteLike(post,usuario);
                        }else{
                            ServiciosLikePost.getInstancia().crear(new LikePost(post,usuario,true));
                        }
                        response.redirect("/redSocial/userArea/" + usuario.getCorreo() + "/perfilUsuario");
                    }

            } catch (Exception e) {
                System.out.println("Error al indicar like en el post: " + e.toString());
            }
            return "";
        });

        get("/redSocial/listaUsuarios", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario logUser = request.session(true).attribute("usuario");

            Usuario user = ServiciosUsuario.getInstancia().find(logUser.getIdUsuario());
            attributes.put("logUser",logUser);
            attributes.put("amigos", user.getAmigos());
            attributes.put("otrosUsuarios",ServiciosUsuario.getInstancia().findNoAmigos(user));
            return new ModelAndView(attributes, "profiles.html");
        }, freeMarkerEngine);

        get("redSocial/userArea/:correo/amigos", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario logUser = request.session(true).attribute("usuario");

            String correoUser = request.params("correo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            attributes.put("logUser",logUser);
            attributes.put("usuario",user);
            return new ModelAndView(attributes, "amigos.html");
        }, freeMarkerEngine);

        get("/json/amigos", (request, response) -> {
            Usuario logUser = request.session(true).attribute("usuario");
            //String correoUser = request.params("correo");
            return ServiciosUsuario.getInstancia().amigosToJSON(logUser);
        },  new JsonTransformer());

        get("redSocial/perfil/:correo/perfilUsuario", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario logUser = request.session(true).attribute("usuario");
            String correoUser = request.params("correo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            attributes.put("logUser",logUser);
            attributes.put("usuario",user);
            attributes.put("posts",ServiciosPost.getInstancia().findByAuthor(logUser));
            attributes.put("fecha_nacimiento", user.getFechaNacimiento());
            attributes.put("pais_origen", user.getPais().getPais());
            attributes.put("ciudad_origen", user.getCiudad());
            attributes.put("lugar_estudio", user.getLugarDeEstudio());
            attributes.put("trabajo", user.getEmpleo());
            attributes.put("albumes", user.getAlbumes());
            for (Post post: ServiciosPost.getInstancia().findByAuthor(logUser)){
                System.out.println(post.getCuerpo());
            }
            return new ModelAndView(attributes, "user-profile.html");
        }, freeMarkerEngine);

        get("/redSocial/userArea/:correo/eliminarAmigo", (request, response) -> {
            String correoUser = request.params("correo");
            String correoAmigo = request.queryParams("amigo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            Usuario amigo = ServiciosUsuario.getInstancia().findByEmail(correoAmigo);
            if(user==null || amigo==null)
                response.redirect("/");
            else{
                ServiciosUsuario.getInstancia().deleteAmistad(user,amigo);
                ServiciosUsuario.getInstancia().deleteAmistad(amigo,user);
                ServiciosUsuario.getInstancia().editar(user);
                response.redirect("/redSocial/userArea/"+correoUser+"/amigos");
            }
            return "";
        });

        get("/redSocial/userArea/:correo/enviarRequest", (request, response) -> {
            String correoUser = request.params("correo");
            String correoAmigo = request.queryParams("amigo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            Usuario amigo = ServiciosUsuario.getInstancia().findByEmail(correoAmigo);
            if(user==null || amigo==null)
                response.redirect("/");
            else{
                ServiciosSolicitudAmistad.getInstancia().crearRequest(user,amigo);
                response.redirect("/redSocial/perfil/"+correoUser+"/perfilUsuario");
            }
            return "";
        });

        post("/redSocial/userArea/:correo/rechazarRequest", (request, response) -> {
            String correoUser = request.params("correo");
            long idRequest = Long.parseLong(request.queryParams("solicitud"));
            SolicitudAmistad solicitud = ServiciosSolicitudAmistad.getInstancia().find(idRequest);
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            Usuario amigo = solicitud.getEmisor();
            if(user==null || amigo==null)
                response.redirect("/");
            else{
                ServiciosSolicitudAmistad.getInstancia().eliminar(solicitud.getIdSolicitud());
                response.redirect("/redSocial/userArea/"+correoUser+"/settings");
            }
            return "";
        });

        post("/redSocial/userArea/:correo/aceptarRequets", (request, response) -> {
            String correoUser = request.params("correo");
            long idRequest = Long.parseLong(request.queryParams("solicitud"));
            SolicitudAmistad solicitud = ServiciosSolicitudAmistad.getInstancia().find(idRequest);

            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            Usuario amigo = solicitud.getEmisor();
            if(user==null || amigo==null)
                response.redirect("/");
            else{
                user.getAmigos().add(amigo);
                amigo.getAmigos().add(user);
                ServiciosUsuario.getInstancia().editar(user);
                ServiciosSolicitudAmistad.getInstancia().eliminar(solicitud.getIdSolicitud());
                response.redirect("/redSocial/userArea/"+correoUser+"/settings");
            }
            return "";
        });

        get("/redSocial/userArea/:correo/settings", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario logUser = request.session(true).attribute("usuario");
            String correoUser = request.params("correo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            attributes.put("logUser",logUser);
            attributes.put("usuario",user);
            attributes.put("solicitudes",user.getSolicitudesRecibidas());
            for (Post post: ServiciosPost.getInstancia().findByAuthor(logUser)){
                System.out.println(post.getCuerpo());
            }
            return new ModelAndView(attributes, "profile-account-setting.html");
        }, freeMarkerEngine);

         post("/crearNuevoAlbum", (request, response) -> {
            try {
                String nombreAlbum = request.queryParams("nombreAlbum");
                String descripcion = request.queryParams("descripcion");
                Usuario logUser = request.session(true).attribute("usuario");

                Album nuevoAlbum = new Album(nombreAlbum,descripcion,logUser,null);
                ServiciosAlbum.getInstancia().editar(nuevoAlbum);

                response.redirect("/redSocial/userArea/" + logUser.getCorreo() + "/perfilUsuario");
            } catch (Exception e) {
                System.out.println("Error al crear album" + e.toString());
            }
            return "";
        });

        post("/insertarImagenAlbum/:idAlbum", (request, response) -> {
            try {
                String imagenRuta = (ServiciosImagen.getInstancia().guardarFoto("fotoAlbum",fotosDir,request));
                String idAlbum = request.params("idAlbum");
                Album albumActual = ServiciosAlbum.getInstancia().find(Long.valueOf(idAlbum));

                Imagen imagen = null;
                if(imagenRuta != null){
                    imagen = new Imagen(imagenRuta,albumActual,null);
                    albumActual.getImagenes().add(imagen);
                }
                Usuario logUser = request.session(true).attribute("usuario");

                ServiciosAlbum.getInstancia().editar(albumActual);

                response.redirect("/redSocial/userArea/" + logUser.getCorreo() + "/perfilUsuario");
            } catch (Exception e) {
                System.out.println("Error al realizar insertar imagen en el album" + e.toString());
            }
            return "";
        });

    }
}