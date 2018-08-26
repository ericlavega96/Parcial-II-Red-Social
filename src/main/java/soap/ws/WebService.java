package soap.ws;

import encapsulaciones.Imagen;
import encapsulaciones.Post;
import encapsulaciones.Usuario;
import entidades.ServiciosActividad;
import entidades.ServiciosNotificaciones;
import entidades.ServiciosPost;
import entidades.ServiciosUsuario;
import logical.Actividad;
import logical.Notificacion;
import logical.Tag;

import javax.jws.WebMethod;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@javax.jws.WebService
public class WebService {
    @WebMethod
    public String holaMundo(String hola){
        return hola;
    }

    @WebMethod
    public List<Post> getAllPost(){
        List<logical.Post> postEncontrados = ServiciosPost.getInstancia().findAll();
        List<Post> resultado = new ArrayList<>();
        System.out.println("Enviado todos los post");
        String tags;
        for(logical.Post postBuscado : postEncontrados) {
            tags = "";
            List<Tag> tagList = new ArrayList(postBuscado.getListaTags());
            for (int i = 0; i < tagList.size(); i++) {
                if (i > 0)
                    tags += ", ";
                tags += tagList.get(i).getTag();
            }
            resultado.add(new Post(postBuscado.getIdPost(),
                    new Usuario(postBuscado.getAutor().getCorreo(), postBuscado.getAutor().getPassword(),
                            postBuscado.getAutor().getNombres(), postBuscado.getAutor().getApellidos()),
                    new Imagen(postBuscado.getFotoPost().getImagen()),
                    postBuscado.getCuerpo(), postBuscado.getFecha()));
        }
        return resultado;
    }

    @WebMethod
    public List<Post> getAllUserPost(String correo){
        logical.Usuario user = ServiciosUsuario.getInstancia().findByEmail(correo);
        List<logical.Post> postEncontrados = ServiciosPost.getInstancia().findByAuthor(user);
        List<Post> resultado = new ArrayList<>();
        System.out.println("Enviado todos los post");
        String tags;
        for(logical.Post postBuscado : postEncontrados) {
            tags = "";
            List<Tag> tagList = new ArrayList(postBuscado.getListaTags());
            for (int i = 0; i < tagList.size(); i++) {
                if (i > 0)
                    tags += ", ";
                tags += tagList.get(i).getTag();
            }
            resultado.add(new Post(postBuscado.getIdPost(),
                    new Usuario(postBuscado.getAutor().getCorreo(), postBuscado.getAutor().getPassword(),
                            postBuscado.getAutor().getNombres(), postBuscado.getAutor().getApellidos()),
                    new Imagen(postBuscado.getFotoPost().getImagen()),
                    postBuscado.getCuerpo(), postBuscado.getFecha(),tags));
        }
        return resultado;
    }

    @WebMethod
    public String subirPost(String correo, String password, String cuerpo, String imagen, String etiquetas){
        logical.Usuario logUser = ServiciosUsuario.getInstancia().findByEmailAndPassword(correo, password);


        if(logUser != null){
            Set<Tag> tags = Tag.crearEtiquetas(etiquetas.split(","));
            logical.Post nuevoPost = new logical.Post(logUser,new logical.Imagen(imagen, null, null),
                    cuerpo,new Date(),null,tags,null);
            Set<logical.Usuario> mencionados = ServiciosUsuario.getInstancia().getAmigosTexto(cuerpo);

            for(logical.Usuario a : mencionados){
                Notificacion notificacion = new Notificacion(a,
                        logUser.getNombres() + " " + logUser.getApellidos() +
                                " te ha mencionado en su nuevo post.", new Date());
                a.getNotificaciones().add(notificacion);
                ServiciosNotificaciones.getInstancia().crear(notificacion);
            }

            ServiciosPost.getInstancia().crear(nuevoPost);

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

            return "Se ha realizado el post exitosamente";
        }



        else
            return "El usuario encontrado no es válido!";
    }
}
