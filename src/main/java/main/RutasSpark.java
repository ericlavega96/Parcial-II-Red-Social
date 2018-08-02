package main;

import entidades.*;
import freemarker.template.Configuration;
import logical.*;
import servicios.Encriptamiento;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;

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
            attributes.put("paises",ServiciosPais.getInstancia().findAll());
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
            attributes.put("albumes", user.getAlbumes());
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


    }
}