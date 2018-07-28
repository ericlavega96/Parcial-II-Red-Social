package main;

import entidades.ServiciosCiudad;
import entidades.ServiciosPais;
import entidades.ServiciosUsuario;
import freemarker.template.Configuration;
import logical.Ciudad;
import logical.Pais;
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
            attributes.put("ciudades",ServiciosCiudad.getInstancia().findAll());
            return new ModelAndView(attributes, "sign-in.ftl");
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
                    response.redirect("/perfilUsuario");
                } else {
                    response.redirect("/iniciarSesion");
                }

            } catch (Exception e) {
                System.out.println("Error al intentar iniciar sesión " + e.toString());
            }
            return "";
        });

        get("/perfilUsuario", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            return new ModelAndView(attributes, "user-profile.html");
        }, freeMarkerEngine);

        post("/registrarUsuario", (request, response) -> {
            //try {
                String nombres = request.queryParams("nombres");
                String apellidos = request.queryParams("apellidos");
                String sexo = request.queryParams("rbMasculino");
                String fechaNacimiento = request.queryParams("fechaNacimiento");
                String pais = request.queryParams("cbBoxPais");
                String ciudad = request.queryParams("cbBoxCiudad");
                String lugarEstudio = request.queryParams("lugarEstudio");
                String empleo = request.queryParams("empleo");
                String correo = request.queryParams("email");
                String password = request.queryParams("password");

                Usuario nuevoUsuario = new Usuario(nombres,apellidos,(sexo!= null) ? "Masculino":"Femenino",new SimpleDateFormat("yyyy-mm-dd").parse(fechaNacimiento), ServiciosCiudad.getInstancia().findByCityAndCountry(ciudad,pais),lugarEstudio,empleo,correo,password,null,false);
                ServiciosUsuario.getInstancia().crear(nuevoUsuario);


            //} catch (Exception e) {
            //    System.out.println("Error al intentar iniciar sesión " + e.toString());
            //}
            return "";
        });

    }
}

