package entidades;

import logical.Album;
import servicios.MetodosDB;

public class ServiciosAlbum extends MetodosDB<Album> {

    private static ServiciosAlbum instancia;

    private ServiciosAlbum(){super(Album.class);}

    public static ServiciosAlbum getInstancia(){
        if(instancia==null){
            instancia = new ServiciosAlbum();
        }
        return instancia;
    }
}
