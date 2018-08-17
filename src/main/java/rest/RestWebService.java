package rest;
import encapsulaciones.*;
import entidades.ServiciosPost;
import entidades.ServiciosUsuario;
import java.util.ArrayList;
import java.util.List;

public class RestWebService {

    public static List<Post> getPostByAutor(String correo){
        logical.Usuario autor = ServiciosUsuario.getInstancia().findByEmail(correo);
        List<logical.Post> posts = ServiciosPost.getInstancia().findByAuthor(autor);
        List<Post> resultSet = new ArrayList<Post>();
        for(logical.Post post : posts)
            resultSet.add(new Post(post.getIdPost(),
                    new Usuario(post.getAutor().getCorreo(),post.getAutor().getPassword(),
                            post.getAutor().getNombres(), post.getAutor().getApellidos()),
                    new Imagen(post.getFotoPost().getImagen()),
                    post.getCuerpo(), post.getFecha()));
        return resultSet;
    }

}
