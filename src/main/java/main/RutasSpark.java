package main;

import entidades.ServiciosPais;
import entidades.ServiciosPost;
import entidades.ServiciosUsuario;
import freemarker.template.Configuration;
import logical.Pais;
import logical.Post;
import logical.Tag;
import logical.Usuario;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.jar.Attributes;

import static main.Main.Encryptamiento;
import static spark.Spark.get;
import static spark.Spark.post;

public class RutasSpark {
    public void iniciarSpark() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
        cfg.setClassForTemplateLoading(Main.class, "/templates");
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(cfg);

        List<Usuario> misUsuarios = new ArrayList<Usuario>();

        get("/", (request, response) -> {
            response.redirect("/login");
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
                        response.cookie("/", "jdklsjfklsjfl",
                                Encryptamiento(correoAVerificar), (60*60*24*7), false, true);
                    }
                    response.redirect("/perfilUsuario/" + logUser.getCorreo());
                } else {
                    response.redirect("/iniciarSesion");
                }

            } catch (Exception e) {
                System.out.println("Error al intentar iniciar sesión " + e.toString());
            }
            return "";
        });

        get("/perfilUsuario/:correo", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Usuario logUser = request.session(true).attribute("usuario");
            String correoUser = request.params("correo");
            Usuario user = ServiciosUsuario.getInstancia().findByEmail(correoUser);
            attributes.put("usuario",logUser);
            attributes.put("fecha_nacimiento", user.getFechaNacimiento());
            attributes.put("pais_origen", user.getPais().getPais());
            attributes.put("ciudad_origen", user.getCiudad());
            attributes.put("lugar_estudio", user.getLugarDeEstudio());
            attributes.put("trabajo", user.getEmpleo());
            attributes.put("albumes", user.getAlbumes());
            for (Post post: ServiciosPost.getInstancia().findByAuthor(logUser)){
                System.out.println(post.getCuerpo());
            }
            return new ModelAndView(attributes, "my-profile-feed.ftl");
        }, freeMarkerEngine);

        post("/registrarUsuario", (request, response) -> {
            try {
                String nombres = request.queryParams("nombres");
                String apellidos = request.queryParams("apellidos");
                String sexo = request.queryParams("rbMasculino");
                String fechaNacimiento = request.queryParams("fechaNacimiento");
                String pais = request.queryParams("cbBoxPais");
                String ciudad = request.queryParams("ciudad");
                String lugarEstudio = request.queryParams("lugarEstudio");
                String empleo = request.queryParams("empleo");
                String correo = request.queryParams("email");
                String password = request.queryParams("password");

                Usuario nuevoUsuario = new Usuario(nombres,apellidos,correo,password,
                        (sexo!= null) ? "Masculino":"Femenino",
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
                Usuario logUser = request.session(true).attribute("usuario");
                String publicacion = request.queryParams("publicacion");
                String[] tags = request.queryParams("tags").split(",");
                Set<Tag> postEtiquetas = Tag.crearEtiquetas(tags);
                Post nuevoPost = new Post(logUser,null,publicacion,new Date(),null,postEtiquetas,null);
                ServiciosPost.getInstancia().crear(nuevoPost);
                response.redirect("/perfilUsuario/" + logUser.getCorreo());
            } catch (Exception e) {
               // System.out.println("Error al realizar post" + e.toString());
           }
            return "";
        });

    }
}

