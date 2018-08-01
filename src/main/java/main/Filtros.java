package main;

import entidades.ServiciosUsuario;
import logical.Usuario;
import servicios.Encriptamiento;

import static spark.Spark.before;

public class Filtros {
    public void aplicarFiltros(){
        before("redSocial/userArea/:correo/*", (request, response) -> {
            // ... check if authenticated
            Usuario logUser = request.session(true).attribute("usuario");
            if (logUser == null || !logUser.getCorreo().equals(request.params("correo"))) {
                response.redirect("/");
            }
        });

        before((request, response) -> {
            // ... check if authenticated
            Usuario logUser = request.session(true).attribute("usuario");
            String username = request.cookie("sr5h464h846s4dhds4h6w9uyh");
            System.out.println("Usuario en la cookie: " + new Encriptamiento().desencriptar(username));
            if (logUser == null && username != null) {
                request.session(true);
                request.session().attribute("usuario",
                            ServiciosUsuario.getInstancia().findByEmail(new Encriptamiento().desencriptar(username)));
            }
        });

        before("redSocial/*", (request, response) -> {
            Usuario logUser = request.session(true).attribute("usuario");
            if (logUser == null && request.cookie("sr5h464h846s4dhds4h6w9uyh") == null)
                response.redirect("/");
        });


    }
}
