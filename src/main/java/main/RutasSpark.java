package main;

import encapsulaciones.Amigo;
import entidades.*;
import freemarker.template.Configuration;
import logical.*;
import servicios.Encriptamiento;
import servicios.JsonTransformer;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
                response.redirect("redSocial/noticias");
                //response.redirect("redSocial/userArea/" + logUser.getCorreo() + "/perfilUsuario");
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

            Usuario tmpUser = request.session(true).attribute("usuario");
            Usuario logUser = ServiciosUsuario.getInstancia().find(tmpUser.getIdUsuario());

            String correoUser = request.params("correo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            attributes.put("logUser",logUser);
            attributes.put("usuario",logUser);
            attributes.put("posts",ServiciosPost.getInstancia().findByAuthor(logUser));
            attributes.put("paises",ServiciosPais.getInstancia().findAllOrdenado());
            attributes.put("fecha_nacimiento", user.convertirFecha());
            attributes.put("pais_origen", user.getPais().getPais());
            attributes.put("ciudad_origen", user.getCiudad());
            attributes.put("lugar_estudio", user.getLugarDeEstudio());
            attributes.put("trabajo", user.getEmpleo());
            attributes.put("albumes", ServiciosAlbum.getInstancia().findAlbumsByUser(logUser));
            attributes.put("actividades",ServiciosActividad.getInstancia().actividadesAmigos(user));
            attributes.put("sugerencias",
                    ServiciosUsuario.getInstancia().findNoAmigos(logUser,
                            ServiciosUsuario.getInstancia().findSugerencia(logUser)));
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String fechaNacimiento = request.queryParams("fechaNacimiento");
                String pais = request.queryParams("cbBoxPais");
                String ciudad = request.queryParams("ciudad");
                String lugarEstudio = request.queryParams("lugarEstudio");
                String empleo = request.queryParams("empleo");
                String correo = request.queryParams("email");
                String password = request.queryParams("password");

                Usuario nuevoUsuario = new Usuario(nombres,apellidos,correo,password,
                        (sexo != null) ? "Masculino":"Femenino",
                        sdf.parse(fechaNacimiento),ServiciosPais.getInstancia()
                        .findByCountry(pais), ciudad,lugarEstudio,empleo,null,false);
                ServiciosUsuario.getInstancia().crear(nuevoUsuario);

                Notificacion notificacion = new Notificacion(nuevoUsuario,"Bienvenido, " + nuevoUsuario.getNombres() + " " +
                        nuevoUsuario.getApellidos() + " a nuestra red social.", new Date());
                ServiciosUsuario.getInstancia().find(nuevoUsuario.getIdUsuario()).getNotificaciones().add(notificacion);
                ServiciosNotificaciones.getInstancia().crear(notificacion);
                Actividad actividad = new Actividad(nuevoUsuario,nuevoUsuario.getNombres() + " " +
                        nuevoUsuario.getApellidos() + " se ha unido a la red social.", new Date());
                ServiciosUsuario.getInstancia().find(nuevoUsuario.getIdUsuario()).getTimeline().add(actividad);
                ServiciosActividad.getInstancia().crear(actividad);


                response.redirect("/login");

            } catch (Exception e) {
                System.out.println("Error al intentar registrar usuario" + e.toString());
            }
            return "";
        });

        post("/publicarPost", (request, response) -> {
            //try {
                String imagenRuta = (ServiciosImagen.getInstancia().guardarFoto("imagen",fotosDir,request));
                Imagen imagen = null;
                if(imagenRuta != null){
                    imagen = new Imagen(imagenRuta,null,null);
                }
                Usuario tmpUser = request.session(true).attribute("usuario");
                Usuario logUser = ServiciosUsuario.getInstancia().find(tmpUser.getIdUsuario());
                String cuerpo = request.queryParams("cuerpo");
                String tags = request.queryParams("tags");
                String geolocation = request.queryParams("geolocalizacion").replace("Ubicación Actual: ","");
                Set<Tag> postEtiquetas = Tag.crearEtiquetas(tags.split(","));
                boolean esPrivado = request.queryParams("esPrivado") == null ? false : true;
                Post nuevoPost = new Post(logUser,imagen,cuerpo,new Date(),null,postEtiquetas,null,geolocation,esPrivado);
                Set<Usuario> mencionados = ServiciosUsuario.getInstancia().getAmigosTexto(cuerpo);
                for(Usuario a : mencionados){
                    Notificacion notificacion = new Notificacion(a,
                            logUser.getNombres() + " " + logUser.getApellidos() +
                                    " te ha mencionado en su nuevo post.", new Date());
                    a.getNotificaciones().add(notificacion);
                    ServiciosNotificaciones.getInstancia().crear(notificacion);
                }

                ServiciosPost.getInstancia().crear(nuevoPost);

                Notificacion notificacion = new Notificacion(logUser,"Has publicado un nuevo post.", new Date());
                logUser.getNotificaciones().add(notificacion);
                ServiciosNotificaciones.getInstancia().crear(notificacion);

                for(Usuario a : logUser.getAmigos()){
                    Notificacion notificacionAmigo = new Notificacion(a,
                            logUser.getNombres() + " " + logUser.getApellidos() +
                                    " ha publicado un nuevo post.", new Date());
                    a.getNotificaciones().add(notificacionAmigo);
                    ServiciosNotificaciones.getInstancia().crear(notificacionAmigo);
                }

                Actividad actividad = new Actividad(logUser,logUser.getNombres() + " " +
                        logUser.getApellidos() + " ha publicado un nuevo post.", new Date());
                logUser.getTimeline().add(actividad);
                ServiciosActividad.getInstancia().crear(actividad);

                response.redirect("/redSocial/userArea/" + logUser.getCorreo() + "/perfilUsuario");
            /*} catch (Exception e) {
                System.out.println("Error al realizar post" + e.toString());
            }*/
            return "";
        });

        post("/comentarPost/:id", (request, response) -> {
            try {
                String comentario = request.queryParams("comentarioNuevo");
                Usuario autor = request.session(true).attribute("usuario");
                Post postActual = ServiciosPost.getInstancia().find(Long.parseLong(request.params("id")));

                ComentarioPost nuevoComentario = new ComentarioPost(comentario,new Date(),autor,postActual);
                ServiciosComentarioPost.getInstancia().crear(nuevoComentario);

                Notificacion notificacion = new Notificacion(autor,"Has comentado en un post de " +
                        postActual.getAutor().getNombres() + " " +
                        postActual.getAutor().getApellidos() + ".", new Date());
                autor.getNotificaciones().add(notificacion);
                ServiciosNotificaciones.getInstancia().crear(notificacion);

                for(Usuario a : autor.getAmigos()){
                    Notificacion notificacionAmigo = new Notificacion(a,
                            autor.getNombres() + " " + autor.getApellidos() +
                                    " ha comentado en el post de " +
                                    postActual.getAutor().getNombres() + " " +
                                    postActual.getAutor().getApellidos() + ".", new Date());
                    a.getNotificaciones().add(notificacionAmigo);
                    ServiciosNotificaciones.getInstancia().crear(notificacionAmigo);
                }

                Actividad actividad = new Actividad(autor,autor.getNombres() + " " +
                        autor.getApellidos() + " ha comentado un post de " + postActual.getAutor().getNombres() +
                        " " + postActual.getAutor().getApellidos() + ".", new Date());
                autor.getTimeline().add(actividad);
                ServiciosActividad.getInstancia().crear(actividad);

                Notificacion notificacionReceptor = new Notificacion(postActual.getAutor(),"Haz recivido un comentario en tu post de parte de" +
                        autor.getNombres() + " " +
                        autor.getApellidos() + ".", new Date());
                postActual.getAutor().getNotificaciones().add(notificacionReceptor);
                ServiciosNotificaciones.getInstancia().crear(notificacionReceptor);

                response.redirect("/redSocial/userArea/" + postActual.getAutor().getCorreo() + "/perfilUsuario");

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

                            Notificacion notificacion = new Notificacion(usuario,"Has dado like al post de " +
                                    post.getAutor().getNombres() + " " +
                                    post.getAutor().getApellidos() + ".", new Date());
                            usuario.getNotificaciones().add(notificacion);
                            ServiciosNotificaciones.getInstancia().crear(notificacion);

                            for(Usuario a : usuario.getAmigos()){
                                Notificacion notificacionAmigo = new Notificacion(a,
                                        usuario.getNombres() + " " + usuario.getApellidos() +
                                                " le ha dado like al post de " +
                                                post.getAutor().getNombres() + " " +
                                                post.getAutor().getApellidos() + ".", new Date());
                                a.getNotificaciones().add(notificacionAmigo);
                                ServiciosNotificaciones.getInstancia().crear(notificacionAmigo);
                            }

                            Actividad actividad = new Actividad(usuario,usuario.getNombres() + " " +
                                    usuario.getApellidos() + " le ha dado like al post de " + post.getAutor().getNombres() +
                                    " " + post.getAutor().getApellidos() + ".", new Date());
                            usuario.getTimeline().add(actividad);
                            ServiciosActividad.getInstancia().crear(actividad);


                            Notificacion notificacionReceptor = new Notificacion(post.getAutor(),"Has recibido un like en tu post de parte de" +
                                    usuario.getNombres() + " " +
                                    usuario.getApellidos() + ".", new Date());
                            post.getAutor().getNotificaciones().add(notificacionReceptor);
                            ServiciosNotificaciones.getInstancia().crear(notificacionReceptor);

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
            Usuario tmpUser = request.session(true).attribute("usuario");
            Usuario logUser = ServiciosUsuario.getInstancia().find(tmpUser.getIdUsuario());

            Usuario user = ServiciosUsuario.getInstancia().find(logUser.getIdUsuario());
            attributes.put("logUser",logUser);
            attributes.put("amigos", user.getAmigos());
            attributes.put("otrosUsuarios",
                    ServiciosUsuario.getInstancia().findNoAmigos(user,ServiciosUsuario
                    .getInstancia().findAll()));
            return new ModelAndView(attributes, "profiles.html");
        }, freeMarkerEngine);

        get("redSocial/userArea/:correo/amigos", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario tmpUser = request.session(true).attribute("usuario");
            Usuario logUser = ServiciosUsuario.getInstancia().find(tmpUser.getIdUsuario());

            String correoUser = request.params("correo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            attributes.put("logUser",logUser);
            attributes.put("usuario",user);
            return new ModelAndView(attributes, "amigos.html");
        }, freeMarkerEngine);

        get("/json/amigos", (request, response) -> {
            Usuario logUser = request.session(true).attribute("usuario");
            Usuario user = ServiciosUsuario.getInstancia().find(logUser.getIdUsuario());
            return ServiciosUsuario.getInstancia().amigosToJSON(user);
        }, JsonTransformer.json());

        get("redSocial/perfil/:correo/perfilUsuario", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario tmpUser = request.session(true).attribute("usuario");
            Usuario logUser = ServiciosUsuario.getInstancia().find(tmpUser.getIdUsuario());
            String correoUser = request.params("correo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            attributes.put("logUser",logUser);
            attributes.put("usuario",user);
            attributes.put("actividades",ServiciosActividad.getInstancia().findByUser(user));
            attributes.put("posts",ServiciosPost.getInstancia().findPostPrivados(logUser,user));
            attributes.put("fecha_nacimiento", user.convertirFecha());
            attributes.put("pais_origen", user.getPais().getPais());
            attributes.put("ciudad_origen", user.getCiudad());
            attributes.put("lugar_estudio", user.getLugarDeEstudio());
            attributes.put("trabajo", user.getEmpleo());
            attributes.put("albumes", user.getAlbumes());
            attributes.put("sugerencias",
                    ServiciosUsuario.getInstancia().findNoAmigos(logUser,
                            ServiciosUsuario.getInstancia().findSugerencia(logUser)));
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

                Notificacion notificacion = new Notificacion(user,"Haz dejado de ser amigo con " +
                        amigo.getNombres() + " " +
                        amigo.getApellidos() + ".", new Date());
                user.getNotificaciones().add(notificacion);
                ServiciosNotificaciones.getInstancia().crear(notificacion);

                for(Usuario a : user.getAmigos()){
                    Notificacion notificacionAmigo = new Notificacion(a,
                            user.getNombres() + " " + user.getApellidos() +
                                    " ha dejado de ser amigo de " +
                                    amigo.getNombres() + " " +
                                    amigo.getApellidos() + ".", new Date());
                    a.getNotificaciones().add(notificacionAmigo);
                    ServiciosNotificaciones.getInstancia().crear(notificacionAmigo);
                }

                Actividad actividad = new Actividad(user,user.getNombres() + " " +
                        user.getApellidos() + " ha dejado de ser amigo de " + amigo.getNombres() +
                        " " + amigo.getApellidos() + ".", new Date());
                user.getTimeline().add(actividad);
                ServiciosActividad.getInstancia().crear(actividad);

                Notificacion notificacionReceptor = new Notificacion(amigo,user.getNombres() + " " +
                        user.getApellidos() + " ha dejado de ser tu amigo.", new Date());
                amigo.getNotificaciones().add(notificacionReceptor);
                ServiciosNotificaciones.getInstancia().crear(notificacionReceptor);

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


                Notificacion notificacion = new Notificacion(user,"Haz enviado una solicitud de amistad a " +
                        amigo.getNombres() + " " +
                        amigo.getApellidos() + ".", new Date());
                user.getNotificaciones().add(notificacion);
                ServiciosNotificaciones.getInstancia().crear(notificacion);

                Notificacion notificacionReceptor = new Notificacion(amigo,user.getNombres() + " " +
                        user.getApellidos() + " te ha enviado una solicitud de amistad.", new Date());
                amigo.getNotificaciones().add(notificacionReceptor);
                ServiciosNotificaciones.getInstancia().crear(notificacionReceptor);


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

                Notificacion notificacion = new Notificacion(user,"Haz rechazado la solicitud de amistad de " +
                        amigo.getNombres() + " " +
                        amigo.getApellidos() + ".", new Date());
                user.getNotificaciones().add(notificacion);
                ServiciosNotificaciones.getInstancia().crear(notificacion);


                Notificacion notificacionReceptor = new Notificacion(amigo,user.getNombres() + " " +
                        user.getApellidos() + " ha rechazado tu solicitud de amistad.", new Date());
                amigo.getNotificaciones().add(notificacionReceptor);
                ServiciosNotificaciones.getInstancia().crear(notificacionReceptor);

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
                Notificacion notificacion = new Notificacion(user,"Haz aceptado la solicitud de amistad a " +
                        amigo.getNombres() + " " +
                        amigo.getApellidos() + ".", new Date());
                user.getNotificaciones().add(notificacion);
                ServiciosNotificaciones.getInstancia().crear(notificacion);

                for(Usuario a : user.getAmigos()){
                    Notificacion notificacionAmigo = new Notificacion(a,
                            user.getNombres() + " " + user.getApellidos() +
                                    " ha añadido a  " +
                                    amigo.getNombres() + " " +
                                    amigo.getApellidos() + " como amigo.", new Date());
                    a.getNotificaciones().add(notificacionAmigo);
                    ServiciosNotificaciones.getInstancia().crear(notificacionAmigo);
                }

                Actividad actividad = new Actividad(user,user.getNombres() + " " +
                        user.getApellidos() + " ha aceptado la solicitud de amistad " + amigo.getNombres() +
                        " " + amigo.getApellidos() + ".", new Date());
                user.getTimeline().add(actividad);
                ServiciosActividad.getInstancia().crear(actividad);

                Notificacion notificacionReceptor = new Notificacion(amigo,user.getNombres() + " " +
                        user.getApellidos() + " ha aceptado tu solicitud de amistad.", new Date());
                amigo.getNotificaciones().add(notificacionReceptor);
                ServiciosNotificaciones.getInstancia().crear(notificacionReceptor);

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
            Usuario tmpUser = request.session(true).attribute("usuario");
            Usuario logUser = ServiciosUsuario.getInstancia().find(tmpUser.getIdUsuario());
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
                Usuario tmpUser = request.session(true).attribute("usuario");
            Usuario logUser = ServiciosUsuario.getInstancia().find(tmpUser.getIdUsuario());

                Album nuevoAlbum = new Album(nombreAlbum,descripcion,logUser,new Date());
                ServiciosAlbum.getInstancia().editar(nuevoAlbum);

                Notificacion notificacion = new Notificacion(logUser,"Haz publicado un nuevo album llamado " +
                        nombreAlbum + ".", new Date());
                logUser.getNotificaciones().add(notificacion);
                ServiciosNotificaciones.getInstancia().crear(notificacion);

                for(Usuario a : logUser.getAmigos()){
                    Notificacion notificacionAmigo = new Notificacion(a,
                            logUser.getNombres() + " " + logUser.getApellidos() +
                                    " ha publicado un nuevo album titulado " + nombreAlbum + ".", new Date());
                    a.getNotificaciones().add(notificacionAmigo);
                    ServiciosNotificaciones.getInstancia().crear(notificacionAmigo);
                }

                Actividad actividad = new Actividad(logUser,logUser.getNombres() + " " +
                        logUser.getApellidos() + " ha publicado un nuevo album titulado " + nombreAlbum +
                        ".", new Date());
                logUser.getTimeline().add(actividad);
                ServiciosActividad.getInstancia().crear(actividad);

                response.redirect("/redSocial/userArea/" + logUser.getCorreo() + "/perfilUsuario");
            } catch (Exception e) {
                System.out.println("Error al crear album " + e.toString());
            }
            return "";
        });

        post("/insertarImagenAlbum/:idAlbum", (request, response) -> {
            try {
                String imagenRuta = (ServiciosImagen.getInstancia().guardarFoto("fotoAlbum",fotosDir,request));
                String idAlbum = request.params("idAlbum");
                String etiquetados = request.queryParams("etiquetadosValores");

                Album albumActual = ServiciosAlbum.getInstancia().find(Long.valueOf(idAlbum));
                Set<Usuario> usuariosEtiquetados = ServiciosUsuario.listaUsuariosEtiquetados(etiquetados.split(","));

                if(imagenRuta != null){
                    Imagen imagen = new Imagen(imagenRuta,albumActual,new HashSet<>());
                    imagen.setFechaPublicacion(new Date());
                    ServiciosImagen.getInstancia().crear(imagen);
                    for(Usuario userTag : usuariosEtiquetados){
                        userTag.getListaImagenesEtiquetadas().add(imagen);
                        ServiciosUsuario.getInstancia().editar(userTag);
                    }

                }
                Usuario tmpUser = request.session(true).attribute("usuario");
                Usuario logUser = ServiciosUsuario.getInstancia().find(tmpUser.getIdUsuario());

                //ServiciosAlbum.getInstancia().editar(albumActual);

                Notificacion notificacion = new Notificacion(logUser,"Haz publicado un nuevo imagen en " +
                        albumActual.getNombre() + ".", new Date());
                logUser.getNotificaciones().add(notificacion);
                ServiciosNotificaciones.getInstancia().crear(notificacion);

                for(Usuario a : usuariosEtiquetados){
                    Notificacion notificacionAmigo = new Notificacion(a,
                            logUser.getNombres() + " " + logUser.getApellidos() +
                                    " te ha etiquetado en el album " +
                                    albumActual.getNombre() + ".", new Date());
                    a.getNotificaciones().add(notificacionAmigo);
                    ServiciosNotificaciones.getInstancia().crear(notificacionAmigo);
                }

                response.redirect("/redSocial/userArea/" + logUser.getCorreo() + "/perfilUsuario");
            } catch (Exception e) {
                System.out.println("Error al realizar insertar imagen en el album" + e.toString());
            }
            return "";
        });

        get("/logout", (request, response) ->
        {
            Session ses = request.session(true);
            ses.invalidate();
            response.removeCookie("sr5h464h846s4dhds4h6w9uyh");
            response.redirect("/");
            return "";
        });

        get("redSocial/admin/:correo/zonaAdmin", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario tmpUser = request.session(true).attribute("usuario");
            Usuario logUser = ServiciosUsuario.getInstancia().find(tmpUser.getIdUsuario());

            String correoUser = request.params("correo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            attributes.put("logUser",logUser);
            attributes.put("usuario",user);
            attributes.put("listaUsuarios",ServiciosUsuario.getInstancia().findAll());
            return new ModelAndView(attributes, "admin-cuentas.html");
        }, freeMarkerEngine);

        get("/visualizarImagen/:idAlbum/:idImagen", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario tmpUser = request.session(true).attribute("usuario");
            Usuario logUser = ServiciosUsuario.getInstancia().find(tmpUser.getIdUsuario());
            String idImagen = request.params("idImagen");
            attributes.put("logUser",logUser);
            attributes.put("foto",ServiciosImagen.getInstancia().find(Long.valueOf(idImagen)));
            return new ModelAndView(attributes, "visualizarFotoAlbum.html");
        }, freeMarkerEngine);


        get("/procesarLikeFoto/:idAlbum/:idImagen", (request, response) -> {
            try {
                Usuario usuario = request.session(true).attribute("usuario");
                String imagenId = request.params("idImagen");
                String albumFoto = request.params("idAlbum");

                if(usuario != null){
                    Imagen imagen = ServiciosImagen.getInstancia().find(Long.parseLong(imagenId));
                    if(ServiciosImagen.getInstancia().findUserLike(usuario,imagen)){
                        ServiciosLikeImagen.getInstancia().deleteLike(imagen,usuario);
                    }else{
                        ServiciosLikeImagen.getInstancia().crear(new LikeImagen(imagen,usuario,true));
                        Notificacion notificacion = new Notificacion(usuario,"Haz dado like al post de " +
                                imagen.getAlbum().getUsuario().getNombres() + " " +
                                imagen.getAlbum().getUsuario().getApellidos() + ".", new Date());
                        usuario.getNotificaciones().add(notificacion);
                        ServiciosNotificaciones.getInstancia().crear(notificacion);

                        for(Usuario a : usuario.getAmigos()){
                            Notificacion notificacionAmigo = new Notificacion(a,
                                    usuario.getNombres() + " " + usuario.getApellidos() +
                                            " le ha dado like al post de " +
                                            imagen.getAlbum().getUsuario().getNombres() + " " +
                                            imagen.getAlbum().getUsuario().getApellidos() + ".", new Date());
                            a.getNotificaciones().add(notificacionAmigo);
                            ServiciosNotificaciones.getInstancia().crear(notificacionAmigo);
                        }

                        Actividad actividad = new Actividad(usuario,usuario.getNombres() + " " +
                                usuario.getApellidos() + " le ha dado like al post de " + imagen.getAlbum().getUsuario().getNombres() +
                                " " + imagen.getAlbum().getUsuario().getApellidos() + ".", new Date());
                        usuario.getTimeline().add(actividad);
                        ServiciosActividad.getInstancia().crear(actividad);


                        Notificacion notificacionReceptor = new Notificacion(imagen.getAlbum().getUsuario(),"Haz recivido un like en una foto de parte de " +
                                usuario.getNombres() + " " +
                                usuario.getApellidos() + ".", new Date());
                        imagen.getAlbum().getUsuario().getNotificaciones().add(notificacionReceptor);
                        ServiciosNotificaciones.getInstancia().crear(notificacionReceptor);
                    }
                    response.redirect("/visualizarImagen/" + albumFoto + "idAlbum/" + imagenId);
                }

            } catch (Exception e) {
                System.out.println("Error al indicar like en la foto: " + e.toString());
            }
            return "";
        });

        post("/comentarFoto/:idAlbum/:idImagen", (request, response) -> {
            try {
                String comentario = request.queryParams("comentarioNuevo");
                Usuario autor = request.session(true).attribute("usuario");
                Imagen imagenActual = ServiciosImagen.getInstancia().find(Long.parseLong(request.params("idImagen")));
                String imagenId = request.params("idImagen");
                String albumFoto = request.params("idAlbum");

                ComentarioFoto nuevoComentario = new ComentarioFoto(comentario,new Date(),autor,imagenActual);
                ServiciosComentarioFoto.getInstancia().crear(nuevoComentario);

                Notificacion notificacion = new Notificacion(autor,"Haz comentado en una foto de " +
                        imagenActual.getAlbum().getUsuario().getNombres() + " " +
                        imagenActual.getAlbum().getUsuario().getApellidos() + ".", new Date());
                autor.getNotificaciones().add(notificacion);
                ServiciosNotificaciones.getInstancia().crear(notificacion);

                for(Usuario a : autor.getAmigos()){
                    Notificacion notificacionAmigo = new Notificacion(a,
                            autor.getNombres() + " " + autor.getApellidos() +
                                    " ha comentado en una foto de " +
                                    imagenActual.getAlbum().getUsuario().getNombres() + " " +
                                    imagenActual.getAlbum().getUsuario().getApellidos() + ".", new Date());
                    a.getNotificaciones().add(notificacionAmigo);
                    ServiciosNotificaciones.getInstancia().crear(notificacionAmigo);
                }

                Actividad actividad = new Actividad(autor,autor.getNombres() + " " +
                        autor.getApellidos() + " ha comentado una foto de " +
                        imagenActual.getAlbum().getUsuario().getNombres() +
                        " " + imagenActual.getAlbum().getUsuario().getApellidos() + ".", new Date());
                autor.getTimeline().add(actividad);
                ServiciosActividad.getInstancia().crear(actividad);

                Notificacion notificacionReceptor = new Notificacion(imagenActual.getAlbum().getUsuario(),
                        autor.getNombres() + " " + autor.getApellidos() + " ha comentado tu foto.", new Date());
                imagenActual.getAlbum().getUsuario().getNotificaciones().add(notificacionReceptor);
                ServiciosNotificaciones.getInstancia().crear(notificacionReceptor);

                response.redirect("/visualizarImagen/" + albumFoto + "idAlbum/" + imagenId);

            } catch (Exception e) {
                System.out.println("Error al publicar comentario en la foto: " + e.toString());
            }
            return "";
        });

        post("/editarPost/:id", (request, response) -> {
            try{
                String idPostActual = request.params("id");

                Post postAEditar = ServiciosPost.getInstancia().find(Long.parseLong(idPostActual));

                String cuerpo = request.queryParams("cuerpo");

                postAEditar.setCuerpo(cuerpo);
                ServiciosPost.getInstancia().editar(postAEditar);

                Usuario logUser = request.session(true).attribute("usuario");
                response.redirect("/redSocial/userArea/"+logUser.getCorreo()+"/perfilUsuario");

            }catch (Exception e){
                System.out.println("Error al editar el post: " + e.toString());
            }
            return "";
        });

        post("/eliminarPost/:id", (request, response) -> {
            try{
                String idPostActual = request.params("id");
                ServiciosPost.getInstancia().eliminar(Long.parseLong(idPostActual));

                Usuario logUser = request.session(true).attribute("usuario");
                response.redirect("/redSocial/userArea/"+logUser.getCorreo()+"/perfilUsuario");

            }catch (Exception e){
                System.out.println("Error al eliminar post: " + e.toString());
            }
            return "";
        });

        post("/editarInformacionGeneral/fechaDeNacimiento/:id", (request, response) -> {
            try{
                String idUsuario = request.params("id");

                Usuario usuarioAEditar = ServiciosUsuario.getInstancia().find(Long.parseLong(idUsuario));

                String fechaNacimiento = request.queryParams("fechaNacimiento");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                Usuario logUser = request.session(true).attribute("usuario");

                usuarioAEditar.setFechaNacimiento(sdf.parse(fechaNacimiento));
                ServiciosUsuario.getInstancia().editar(usuarioAEditar);
                response.redirect("/redSocial/userArea/"+logUser.getCorreo()+"/perfilUsuario");

            }catch (Exception e){
                System.out.println("Error al editar la fecha de nacimiento del usuario: " + e.toString());
            }
            return "";
        });

        post("/editarInformacionGeneral/lugarDeNacimiento/:id", (request, response) -> {
            try{
                String idUsuario = request.params("id");

                Usuario usuarioAEditar = ServiciosUsuario.getInstancia().find(Long.parseLong(idUsuario));

                String paisNacimiento = request.queryParams("cbBoxPais");
                String ciudadDeNacimiento = request.queryParams("ciudad");

                Usuario logUser = request.session(true).attribute("usuario");

                usuarioAEditar.setPais(ServiciosPais.getInstancia().findByCountry(paisNacimiento));
                usuarioAEditar.setCiudad(ciudadDeNacimiento);
                ServiciosUsuario.getInstancia().editar(usuarioAEditar);
                response.redirect("/redSocial/userArea/"+logUser.getCorreo()+"/perfilUsuario");

            }catch (Exception e){
                System.out.println("Error al editar el lugar de nacimiento del usuario: " + e.toString());
            }
            return "";
        });

        post("/editarInformacionGeneral/centroEstudio/:id", (request, response) -> {
            try{
                String idUsuario = request.params("id");

                Usuario usuarioAEditar = ServiciosUsuario.getInstancia().find(Long.parseLong(idUsuario));

                String centroEstudio = request.queryParams("centroEstudio");

                Usuario logUser = request.session(true).attribute("usuario");

                usuarioAEditar.setLugarDeEstudio(centroEstudio);
                ServiciosUsuario.getInstancia().editar(usuarioAEditar);
                response.redirect("/redSocial/userArea/"+logUser.getCorreo()+"/perfilUsuario");

            }catch (Exception e){
                System.out.println("Error al editar el centro de estudio del usuario: " + e.toString());
            }
            return "";
        });

        post("/editarInformacionGeneral/empleo/:id", (request, response) -> {
            try{
                String idUsuario = request.params("id");

                Usuario usuarioAEditar = ServiciosUsuario.getInstancia().find(Long.parseLong(idUsuario));

                String empleo = request.queryParams("empleo");

                Usuario logUser = request.session(true).attribute("usuario");


                usuarioAEditar.setEmpleo(empleo);
                ServiciosUsuario.getInstancia().editar(usuarioAEditar);
                response.redirect("/redSocial/userArea/"+logUser.getCorreo()+"/perfilUsuario");

            }catch (Exception e){
                System.out.println("Error al editar el empleo del usuario: " + e.toString());
            }
            return "";
        });

        get("redSocial/noticias", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            Usuario tmpUser = request.session(true).attribute("usuario");
            Usuario logUser = ServiciosUsuario.getInstancia().find(tmpUser.getIdUsuario());

            attributes.put("logUser",logUser);
            attributes.put("cantAmigos", logUser.getAmigos().size());
            attributes.put("posts",ServiciosPost.getInstancia().postAmigosUser(logUser));
            attributes.put("sugerencias",
                    ServiciosUsuario.getInstancia().findNoAmigos(logUser,
                            ServiciosUsuario.getInstancia().findSugerencia(logUser)));
            for (Album album : ServiciosAlbum.getInstancia().findAlbumsByUser(logUser)){
                System.out.println("Album cargado: " + String.valueOf(album.getIdAlbum()));
            }
            for (Post post: ServiciosPost.getInstancia().findByAuthor(logUser)){
                System.out.println(post.getCuerpo());
            }
            return new ModelAndView(attributes, "news-feed.html");
        }, freeMarkerEngine);

        post("/cambiarFotoPerfil",((request, response) -> {
            try {
                String imagenRuta = (ServiciosImagen.getInstancia().guardarFoto("imagen", fotosDir, request));
                Imagen imagen = null;
                if (imagenRuta != null) {
                    imagen = new Imagen(imagenRuta, null, null);
                }
                Usuario tmpUsuario = request.session(true).attribute("usuario");
                Usuario logUser = ServiciosUsuario.getInstancia().find(Long.valueOf(tmpUsuario.getIdUsuario()));
                logUser.setFotoPerfil(imagen);
                ServiciosUsuario.getInstancia().editar(logUser);
                System.out.println("La foto de perfil ha sido actualizada con éxito");
                response.redirect("/redSocial/userArea/"+logUser.getCorreo()+"/perfilUsuario");
            }catch (Exception e){
                System.out.println("Error al cambiar la imagen de perfil del usuario " + e.toString());
            }
            return "";
        }));

        get("redSocial/admin/eliminarCorreo/:correo", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario logUser = request.session(true).attribute("usuario");
            String correoUser = request.params("correo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            if(!user.isAdmin()) {
                user.setFotoPerfil(null);
                user.setPais(null);
                for(Album a:user.getAlbumes())
                    ServiciosAlbum.getInstancia().eliminar(a.getIdAlbum());
                for(Usuario a:user.getAmigos()) {
                    ServiciosUsuario.getInstancia().deleteAmistad(user,a);
                    ServiciosUsuario.getInstancia().deleteAmistad(a,user);
                    ServiciosUsuario.getInstancia().editar(user);
                }
                user.getAmigos().clear();
                for(ComentarioPost c : user.getComentarios())
                    ServiciosComentarioPost.getInstancia().eliminar(c.getId());
                for(ComentarioFoto c : user.getComentariosAlbum())
                    ServiciosComentarioFoto.getInstancia().eliminar(c.getId());
                for(Actividad t:user.getTimeline())
                    ServiciosActividad.getInstancia().eliminar(t.getIdActividad());
                for(Notificacion n:user.getNotificaciones())
                    ServiciosNotificaciones.getInstancia().eliminar(n.getIdActividad());
                ServiciosUsuario.getInstancia().deleteUser(user);
            }
            response.redirect("/redSocial/admin/"+logUser.getCorreo()+"/zonaAdmin");

            return "";
        });

        post("/cambiarPrivacidadPost/:id", (request, response) -> {
            try{
                String idPostActual = request.params("id");

                Post postAEditar = ServiciosPost.getInstancia().find(Long.parseLong(idPostActual));

                boolean esPrivado = request.queryParams("esPrivado-edit") == null ? false: true;
                Usuario logUser = request.session(true).attribute("usuario");

                postAEditar.setEsPrivado(esPrivado);
                ServiciosPost.getInstancia().editar(postAEditar);
                response.redirect("/redSocial/userArea/"+logUser.getCorreo()+"/perfilUsuario");

            }catch (Exception e){
                System.out.println("Error al editar la privacidad del post: " + e.toString());
            }
            return "";
        });

        get("/json/correos", (request, response) -> {
            List<Usuario> users = ServiciosUsuario.getInstancia().findAll();
            List<encapsulaciones.Usuario> usuariosJSON = new ArrayList<>();
            for(Usuario u : users)
                usuariosJSON.add(new encapsulaciones.Usuario(u.getCorreo(),null,null,null));
            return usuariosJSON;
        }, JsonTransformer.json());

        get("redSocial/admin/volverAdmin/:correo", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario logUser = request.session(true).attribute("usuario");
            String correoUser = request.params("correo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            if(user!=null) {
                user.setAdmin(true);
                ServiciosUsuario.getInstancia().editar(user);
            }
            response.redirect("/redSocial/admin/"+logUser.getCorreo()+"/zonaAdmin");

            return "";
        });

    }
}