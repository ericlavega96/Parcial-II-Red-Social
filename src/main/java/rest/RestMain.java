package rest;

import com.google.gson.Gson;
import encapsulaciones.PostAuxiliar;
import entidades.ServiciosActividad;
import entidades.ServiciosNotificaciones;
import entidades.ServiciosPost;
import entidades.ServiciosUsuario;
import javafx.geometry.Pos;
import logical.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import servicios.JsonTransformer;
import encapsulaciones.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                    Post nuevoPost = null;
                    switch (request.headers("Content-Type")){
                        case ACCEPT_TYPE_JSON:
                            System.out.println("JSON recibido: " + request.body());
                            nuevoPost = new Gson().fromJson(request.body(), Post.class);
                            break;
                        case ACCEPT_TYPE_XML:
                            nuevoPost = serializer.read(Post.class,request.body());
                            break;
                        default:
                            throw new IllegalArgumentException("Error el formato no disponible");
                    }

                    logical.Usuario logUser =  ServiciosUsuario.getInstancia().findByEmailAndPassword(nuevoPost.getAutor().getCorreo(), nuevoPost.getAutor().getPassword());
                    if(logUser != null){
                        logical.Imagen imagenPost = new Imagen(nuevoPost.getImagen().getImagen(),null,null);
                        Set<Tag> tagList = Tag.crearEtiquetas(nuevoPost.getEtiquetas().split(","));

                        logical.Post restPost = new logical.Post(logUser,imagenPost,nuevoPost.getCuerpo(),new Date(),
                                null,tagList,null);
                        ServiciosPost.getInstancia().crear(restPost);

                        Set<logical.Usuario> mencionados = ServiciosUsuario.getInstancia().usuariosMencionados(nuevoPost.getCuerpo());
                        for(logical.Usuario a : mencionados){
                            Notificacion notificacion = new Notificacion(a,
                                    logUser.getNombres() + " " + logUser.getApellidos() +
                                            " te ha mencionado en su nuevo post.", new Date());
                            a.getNotificaciones().add(notificacion);
                            ServiciosNotificaciones.getInstancia().crear(notificacion);
                        }

                        Notificacion notificacion = new Notificacion(logUser,"Has publicado un nuevo post.", new Date());
                        logUser.getNotificaciones().add(notificacion);
                        ServiciosNotificaciones.getInstancia().crear(notificacion);

                        for(logical.Usuario a : logUser.getAmigos()){
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


                        return restPost;
                    }

                    return null;
                },JsonTransformer.json());
            });
        });
    }

}
