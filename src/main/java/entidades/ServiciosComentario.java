package entidades;

import logical.Comentario;
import servicios.MetodosDB;

public class ServiciosComentario extends MetodosDB<Comentario> {

    private static ServiciosComentario instancia;

    private ServiciosComentario(){super(Comentario.class);}

    public static ServiciosComentario getInstancia(){
        if(instancia==null){
            instancia = new ServiciosComentario();
        }
        return instancia;
    }
}
