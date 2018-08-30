package soap.ws;

import encapsulaciones.Imagen;
import encapsulaciones.Post;
import encapsulaciones.Usuario;
import entidades.*;
import logical.Actividad;
import logical.Notificacion;
import logical.Tag;

import javax.jws.WebMethod;
import java.io.IOException;
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
                    postBuscado.getCuerpo(), postBuscado.getFecha(),tags, postBuscado.isEsPrivado()));
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
            String imagen = null;
            if(postBuscado.getFotoPost() != null)
                imagen = postBuscado.getFotoPost().getImagen();
            resultado.add(new Post(postBuscado.getIdPost(),
                    new Usuario(postBuscado.getAutor().getCorreo(), postBuscado.getAutor().getPassword(),
                            postBuscado.getAutor().getNombres(), postBuscado.getAutor().getApellidos()),
                    new Imagen(imagen),
                    postBuscado.getCuerpo(), postBuscado.getFecha(),tags, postBuscado.isEsPrivado()));
        }
        return resultado;
    }

    @WebMethod
    public String subirPost(String correo, String password, String cuerpo, String imagen, String etiquetas, boolean privado) {
        logical.Usuario logUser = ServiciosUsuario.getInstancia().findByEmailAndPassword(correo, password);

        try {

            if (logUser != null) {
                Set<Tag> tags = Tag.crearEtiquetas(etiquetas.split(","));
                logical.Imagen tmpIm = null;
                if (imagen.length() > 3) {
                    tmpIm = new logical.Imagen(ServiciosImagen.getInstancia().guardarFotoBase64(imagen), null, null);
                }

                logical.Post nuevoPost = new logical.Post(logUser, tmpIm,
                        cuerpo, new Date(), null, tags, null, privado);
                Set<logical.Usuario> mencionados = ServiciosUsuario.getInstancia().getAmigosTexto(cuerpo);

                for (logical.Usuario a : mencionados) {
                    Notificacion notificacion = new Notificacion(a,
                            logUser.getNombres() + " " + logUser.getApellidos() +
                                    " te ha mencionado en su nuevo post.", new Date());
                    a.getNotificaciones().add(notificacion);
                    ServiciosNotificaciones.getInstancia().crear(notificacion);
                }

                ServiciosPost.getInstancia().crear(nuevoPost);

                Notificacion notificacion = new Notificacion(logUser, "Has publicado un nuevo post.", new Date());
                logUser.getNotificaciones().add(notificacion);
                ServiciosNotificaciones.getInstancia().crear(notificacion);

                for (logical.Usuario a : logUser.getAmigos()) {
                    Notificacion notificacionAmigo = new Notificacion(a,
                            logUser.getNombres() + " " + logUser.getApellidos() +
                                    " ha publicado un nuevo post.", new Date());
                    a.getNotificaciones().add(notificacionAmigo);
                    ServiciosNotificaciones.getInstancia().crear(notificacionAmigo);
                }

                Actividad actividad = new Actividad(logUser, logUser.getNombres() + " " +
                        logUser.getApellidos() + " ha publicado un nuevo post.", new Date());
                logUser.getTimeline().add(actividad);
                ServiciosActividad.getInstancia().crear(actividad);

                return "Se ha realizado el post exitosamente";
            } else
                return "El usuario encontrado no es válido!";
        } catch (IOException ex){
            return "Error al crear el post: " + ex;
        }
    }
}
