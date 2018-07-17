package entidades;

import logical.Tag;
import servicios.MetodosDB;

public class ServiciosTag extends MetodosDB<Tag> {

    private static ServiciosTag instancia;

    private ServiciosTag(){super(Tag.class);}

    public static ServiciosTag getInstancia(){
        if(instancia==null){
            instancia = new ServiciosTag();
        }
        return instancia;
    }
}
