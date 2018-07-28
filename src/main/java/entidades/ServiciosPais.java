package entidades;

import logical.Pais;
import servicios.MetodosDB;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ServiciosPais extends MetodosDB<Pais> {

    private static ServiciosPais instancia;

    private ServiciosPais(){super(Pais.class);}

    public static ServiciosPais getInstancia(){
        if(instancia==null){
            instancia = new ServiciosPais();
        }
        return instancia;
    }
    public Pais findByCountry(String pais){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select p from Pais p where p.pais = :pais");
        query.setParameter("pais", pais);

        Pais resultado = (Pais)query.getSingleResult();
        return resultado;

    }

}
