package entidades;

import logical.Album;
import servicios.MetodosDB;

public class ServiciosComentarioFoto extends MetodosDB<Album> {
    private static ServiciosComentarioFoto instancia;

    private ServiciosComentarioFoto(){super(Album.class);}

    public static ServiciosComentarioFoto getInstancia(){
        if(instancia==null){
            instancia = new ServiciosComentarioFoto();
        }
        return instancia;
    }
}
