package entidades;

import logical.Imagen;
import servicios.MetodosDB;

public class ServiciosImagen extends MetodosDB<Imagen> {

    private static ServiciosImagen instancia;

    private ServiciosImagen(){super(Imagen.class);}

    public static ServiciosImagen getInstancia(){
        if(instancia==null){
            instancia = new ServiciosImagen();
        }
        return instancia;
    }
}
