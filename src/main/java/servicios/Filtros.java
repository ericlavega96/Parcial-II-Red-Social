package servicios;

import logical.Usuario;
import static spark.Spark.*;


public class Filtros {
    public void aplicarFiltros() {
        before("/*", (request, response) -> {
            // ... check if authenticated
            Usuario logUser = request.session(true).attribute("usuario");
            if (logUser == null) {
                response.redirect("/login");
            }
        });
    }
}
