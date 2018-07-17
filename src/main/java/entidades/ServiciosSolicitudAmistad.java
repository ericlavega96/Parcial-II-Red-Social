package entidades;

import logical.SolicitudAmistad;
import servicios.MetodosDB;

public class ServiciosSolicitudAmistad extends MetodosDB<SolicitudAmistad> {

    private static ServiciosSolicitudAmistad instancia;

    private ServiciosSolicitudAmistad(){super(SolicitudAmistad.class);}

    public static ServiciosSolicitudAmistad getInstancia(){
        if(instancia==null){
            instancia = new ServiciosSolicitudAmistad();
        }
        return instancia;
    }
}
