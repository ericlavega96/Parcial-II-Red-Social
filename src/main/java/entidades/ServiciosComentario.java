package entidades;

import logical.ComentarioPost;
import servicios.MetodosDB;

public class ServiciosComentario extends MetodosDB<ComentarioPost> {

    private static ServiciosComentario instancia;

    private ServiciosComentario(){super(ComentarioPost.class);}

    public static ServiciosComentario getInstancia(){
        if(instancia==null){
            instancia = new ServiciosComentario();
        }
        return instancia;
    }
}
