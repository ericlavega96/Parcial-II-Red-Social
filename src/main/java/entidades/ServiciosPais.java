package entidades;

import logical.Pais;
import servicios.MetodosDB;

public class ServiciosPais extends MetodosDB<Pais> {

    private static ServiciosPais instancia;

    private ServiciosPais(){super(Pais.class);}

    public static ServiciosPais getInstancia(){
        if(instancia==null){
            instancia = new ServiciosPais();
        }
        return instancia;
    }
}
