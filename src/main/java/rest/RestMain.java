package rest;

import com.google.gson.Gson;
import encapsulaciones.PostAuxiliar;
import entidades.ServiciosActividad;
import entidades.ServiciosNotificaciones;
import entidades.ServiciosPost;
import entidades.ServiciosUsuario;
import logical.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import servicios.JsonTransformer;
import encapsulaciones.Post;

import java.util.Date;
import java.util.Set;

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
                    PostAuxiliar nuevoPost = null;
                    logical.Post restPost = null;
                    switch (request.headers("Content-Type")){
                        case ACCEPT_TYPE_JSON:
                            //System.out.println("JSON recibido: " + request.body());
                            nuevoPost = new Gson().fromJson(request.body(), PostAuxiliar.class);
                            break;
                        case ACCEPT_TYPE_XML:
                            nuevoPost = serializer.read(PostAuxiliar.class,request.body());
                            break;
                        default:
                            throw new IllegalArgumentException("Error el formato no disponible");
                    }

                    logical.Usuario usuario = ServiciosUsuario.getInstancia().findByEmailAndPassword(nuevoPost.getCorreo(),nuevoPost.getPassword());
                    if(usuario != null){
                        logical.Imagen imagen = new Imagen(nuevoPost.getImagen(),null,null);
                        Set<logical.Tag> tagList = logical.Tag.crearEtiquetas(nuevoPost.getTags().split(","));
                        restPost = new logical.Post(usuario,imagen, nuevoPost.getCuerpo(),new Date(),null,tagList,null);
                        ServiciosPost.getInstancia().crear(restPost);
                        System.out.println("El post ha sido creado con éxito");

                        Set<logical.Usuario> mencionados = ServiciosUsuario.getInstancia().getAmigosTexto(nuevoPost.getCuerpo());
                        for(logical.Usuario a : mencionados){
                            Notificacion notificacion = new Notificacion(a,
                                    usuario.getNombres() + " " + usuario.getApellidos() +
                                            " te ha mencionado en su nuevo post.", new Date());
                            a.getNotificaciones().add(notificacion);
                            ServiciosNotificaciones.getInstancia().crear(notificacion);
                        }

                        Notificacion notificacion = new Notificacion(usuario,"Has publicado un nuevo post.", new Date());
                        usuario.getNotificaciones().add(notificacion);
                        ServiciosNotificaciones.getInstancia().crear(notificacion);

                        for(logical.Usuario a : usuario.getAmigos()){
                            Notificacion notificacionAmigo = new Notificacion(a,
                                    usuario.getNombres() + " " + usuario.getApellidos() +
                                            " ha publicado un nuevo post.", new Date());
                            a.getNotificaciones().add(notificacionAmigo);
                            ServiciosNotificaciones.getInstancia().crear(notificacionAmigo);
                        }

                        Actividad actividad = new Actividad(usuario,usuario.getNombres() + " " +
                                usuario.getApellidos() + " ha publicado un nuevo post.", new Date());
                        usuario.getTimeline().add(actividad);
                        ServiciosActividad.getInstancia().crear(actividad);

                    }

                    return nuevoPost;
                },JsonTransformer.json());
            });
        });
    }

}
