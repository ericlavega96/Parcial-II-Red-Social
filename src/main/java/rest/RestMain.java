package rest;

import com.google.gson.Gson;
import entidades.ServiciosPost;
import entidades.ServiciosUsuario;
import logical.Imagen;
import logical.Usuario;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import servicios.JsonTransformer;
import encapsulaciones.Post;

import java.util.List;

import static spark.Spark.*;

public class RestMain {
    public final static String ACCEPT_TYPE_JSON = "application/json";
    public final static String ACCEPT_TYPE_XML = "application/xml";

    public void iniciarServicioRest() {

        Serializer serializer = new Persister();

        path("/rest",() -> {
            //filtros especificos:
            afterAfter("/*", (request, response) -> { //indicando que todas las llamadas retorna un json.
                if(request.headers("Accept").equalsIgnoreCase(ACCEPT_TYPE_XML)){
                    response.header("Content-Type", ACCEPT_TYPE_XML);
                }else{
                    response.header("Content-Type", ACCEPT_TYPE_JSON);
                }
            });
            path("/posts", () -> {
                //retornar los post de un usuario
                get("/:correo", (request, response) -> {
                    return RestWebService.getPostByAutor(request.params("correo"));
                }, JsonTransformer.json());

                post("/",ACCEPT_TYPE_JSON, (request, response) ->{
                    Post nuevoPost = null;
                    switch (request.headers("Content-Type")){
                        case ACCEPT_TYPE_JSON:
                            nuevoPost = new Gson().fromJson(request.body(),Post.class);
                            break;
                        case ACCEPT_TYPE_XML:
                            nuevoPost = serializer.read(Post.class,request.body());
                            break;
                        default:
                            throw new IllegalArgumentException("Error el formato no disponible");
                    }

                    logical.Usuario usuarioPost =  ServiciosUsuario.getInstancia().findByEmail(nuevoPost.getAutor().getCorreo());
                    logical.Imagen imagenPost = new Imagen(nuevoPost.getImagen().getImagen(),null,null);
                    logical.Post restPost = new logical.Post(usuarioPost,imagenPost,nuevoPost.getCuerpo(),nuevoPost.getFecha(),null,null,null);
                    ServiciosPost.getInstancia().crear(restPost);

                    return restPost;
                },JsonTransformer.json());
            });
        });
    }

}
