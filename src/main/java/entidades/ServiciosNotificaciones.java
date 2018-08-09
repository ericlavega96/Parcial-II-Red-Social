package entidades;

import logical.Notificacion;
import servicios.MetodosDB;

public class ServiciosNotificaciones extends MetodosDB<Notificacion> {
    private static ServiciosNotificaciones instancia;

    private ServiciosNotificaciones(){super(Notificacion.class);}

    public static ServiciosNotificaciones getInstancia(){
        if(instancia==null){
            instancia = new ServiciosNotificaciones();
        }
        return instancia;
    }
}
