package entidades;

import logical.Ciudad;
import servicios.MetodosDB;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ServiciosCiudad extends MetodosDB<Ciudad> {

    private static ServiciosCiudad instancia;

    private ServiciosCiudad(){super(Ciudad.class);}

    public static ServiciosCiudad getInstancia(){
        if(instancia==null){
            instancia = new ServiciosCiudad();
        }
        return instancia;
    }

    public Ciudad findByCityAndCountry(String ciudad,String pais){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select c from Ciudad c where c.ciudad = :ciudad AND c.pais = :pais");
        query.setParameter("ciudad", ciudad);
        query.setParameter("pais", pais);
        Ciudad resultado = (Ciudad)query.getSingleResult();
        return resultado;

    }
}
