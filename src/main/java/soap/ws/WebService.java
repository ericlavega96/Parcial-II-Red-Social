package soap.ws;

import encapsulaciones.Imagen;
import encapsulaciones.Post;
import encapsulaciones.Usuario;
import entidades.ServiciosPost;
import entidades.ServiciosUsuario;
import logical.Tag;

import javax.jws.WebMethod;
import java.util.ArrayList;
import java.util.List;

@javax.jws.WebService
public class WebService {
    @WebMethod
    public String holaMundo(String hola){
        return hola;
    }

    @WebMethod
    public Post getPost(long id){
        logical.Post postBuscado = ServiciosPost.getInstancia().find(id);
        System.out.println("Enviado post #"+id);
        String tags = "";
        List<Tag> tagList = new ArrayList(postBuscado.getListaTags());
        for (int i = 0; i < tagList.size(); i++) {
            if (i > 0)
                tags += ", ";
            tags += tagList.get(i);
        }

        return new Post(id,
                new Usuario(postBuscado.getAutor().getCorreo(),postBuscado.getAutor().getPassword(),
                        postBuscado.getAutor().getNombres(), postBuscado.getAutor().getApellidos()),
                new Imagen(postBuscado.getFotoPost().getImagen()),
                postBuscado.getCuerpo(), postBuscado.getFecha(),tags);
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
                tags += tagList.get(i);
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
                tags += tagList.get(i);
            }
            resultado.add(new Post(postBuscado.getIdPost(),
                    new Usuario(postBuscado.getAutor().getCorreo(), postBuscado.getAutor().getPassword(),
                            postBuscado.getAutor().getNombres(), postBuscado.getAutor().getApellidos()),
                    new Imagen(postBuscado.getFotoPost().getImagen()),
                    postBuscado.getCuerpo(), postBuscado.getFecha(),tags));
        }
        return resultado;
    }
}
