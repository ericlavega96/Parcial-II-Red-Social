package main;

import entidades.ServiciosUsuario;
import freemarker.template.Configuration;
import logical.Usuario;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    }
}

