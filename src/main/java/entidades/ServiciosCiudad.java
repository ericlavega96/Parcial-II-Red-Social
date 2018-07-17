package entidades;

import logical.Ciudad;
import servicios.MetodosDB;

public class ServiciosCiudad extends MetodosDB<Ciudad> {

    private static ServiciosCiudad instancia;

    private ServiciosCiudad(){super(Ciudad.class);}

    public static ServiciosCiudad getInstancia(){
        if(instancia==null){
            instancia = new ServiciosCiudad();
        }
        return instancia;
    }
}
