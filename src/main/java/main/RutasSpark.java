package main;

import freemarker.template.Configuration;
import logical.Usuario;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;

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
                String usernameAVerificar = request.queryParams("username");
                String passwordsAVerificar = request.queryParams("password");
                //String isRecordado = request.queryParams("recordar");

                if (usernameAVerificar != null && passwordsAVerificar != null)
                    response.redirect("/perfilUsuario");
                else
                    response.redirect("/login");

            } catch (Exception e) {
                System.out.println("Error al intentar iniciar sesión " + e.toString());
            }
            return "";
        });

        get("/perfilUsuario", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();

            return new ModelAndView(attributes, "user-profile.ftl");
        }, freeMarkerEngine);
    }
}

