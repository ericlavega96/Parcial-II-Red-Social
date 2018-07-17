package entidades;


import logical.Post;
import servicios.MetodosDB;

public class ServiciosPost extends MetodosDB<Post> {

    private static ServiciosPost instancia;

    private ServiciosPost(){super(Post.class);}

    public static ServiciosPost getInstancia(){
        if(instancia==null){
            instancia = new ServiciosPost();
        }
        return instancia;
    }
}
