package rest;
import encapsulaciones.*;
import entidades.ServiciosPost;
import entidades.ServiciosUsuario;
import logical.Tag;

import java.util.ArrayList;
import java.util.List;

public class RestWebService {

    public static List<Post> getPostByAutor(String correo){
        logical.Usuario autor = ServiciosUsuario.getInstancia().findByEmail(correo);
        List<logical.Post> posts = ServiciosPost.getInstancia().findByAuthor(autor);
        List<Post> resultSet = new ArrayList<Post>();
        String tags;
        for(logical.Post post : posts) {

            tags = "";
            List<Tag> tagList = new ArrayList(post.getListaTags());
            for (int i = 0; i < tagList.size(); i++) {
                if (i > 0)
                    tags += ", ";
                tags += tagList.get(i).getTag();
            }

            resultSet.add(new Post(post.getIdPost(),
                    new Usuario(post.getAutor().getCorreo(), post.getAutor().getPassword(),
                            post.getAutor().getNombres(), post.getAutor().getApellidos()),
                    new Imagen(post.getFotoPost().getImagen()),
                    post.getCuerpo(), post.getFecha(), tags));
        }
        return resultSet;
    }

}
