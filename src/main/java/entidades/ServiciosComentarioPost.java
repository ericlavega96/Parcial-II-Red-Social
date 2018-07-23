package entidades;

import logical.ComentarioPost;
import servicios.MetodosDB;

public class ServiciosComentarioPost extends MetodosDB<ComentarioPost> {

    private static ServiciosComentarioPost instancia;

    private ServiciosComentarioPost(){super(ComentarioPost.class);}

    public static ServiciosComentarioPost getInstancia(){
        if(instancia==null){
            instancia = new ServiciosComentarioPost();
        }
        return instancia;
    }
}
