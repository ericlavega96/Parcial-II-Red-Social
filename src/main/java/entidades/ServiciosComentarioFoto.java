package entidades;

import logical.Album;
import logical.ComentarioFoto;
import servicios.MetodosDB;

public class ServiciosComentarioFoto extends MetodosDB<ComentarioFoto> {
    private static ServiciosComentarioFoto instancia;

    private ServiciosComentarioFoto(){super(ComentarioFoto.class);}

    public static ServiciosComentarioFoto getInstancia(){
        if(instancia==null){
            instancia = new ServiciosComentarioFoto();
        }
        return instancia;
    }
}
