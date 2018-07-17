package entidades;

import logical.Actividad;
import servicios.MetodosDB;

public class ServiciosActividad extends MetodosDB<Actividad> {

    private static ServiciosActividad instancia;

    private ServiciosActividad(){super(Actividad.class);}

    public static ServiciosActividad getInstancia(){
        if(instancia==null){
            instancia = new ServiciosActividad();
        }
        return instancia;
    }
}
