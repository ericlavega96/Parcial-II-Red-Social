package entidades;

import logical.Pais;
import logical.SolicitudAmistad;
import logical.Usuario;
import servicios.MetodosDB;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;

public class ServiciosSolicitudAmistad extends MetodosDB<SolicitudAmistad> {

    private static ServiciosSolicitudAmistad instancia;

    private ServiciosSolicitudAmistad(){super(SolicitudAmistad.class);}

    public static ServiciosSolicitudAmistad getInstancia(){
        if(instancia==null){
            instancia = new ServiciosSolicitudAmistad();
        }
        return instancia;
    }

    public boolean crearRequest(Usuario emisor, Usuario receptor){
        if(getInstancia().existRequest(emisor,receptor)) {
            System.out.println("Ya existía un request previo a este.");
            System.out.println("--------------------------------------------------------------------");
            return false;
        }
        else{
            System.out.println("El usuario " + emisor.getCorreo() + " ha enviado una solicitud a " + receptor.getCorreo());
            System.out.println("--------------------------------------------------------------------");
            getInstancia().crear(new SolicitudAmistad(emisor,receptor));
            return true;
        }
    }

    public boolean existRequest(Usuario emisor, Usuario receptor){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select s from SolicitudAmistad s where s.emisor = :emisor AND s.receptor = " +
                ":receptor");
        query.setParameter("emisor", emisor);
        query.setParameter("receptor", receptor);
        if(query.getResultList().size() == 0)
            return false;
        else
            return true;
    }

    public SolicitudAmistad findByUsers(Usuario emisor, Usuario receptor){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select s from SolicitudAmistad s where s.emisor = :emisor AND s.receptor = " +
                ":receptor");
        query.setParameter("emisor", emisor);
        query.setParameter("receptor", receptor);
        if(query.getResultList().size() == 0)
            return null;
        else
            return (SolicitudAmistad) query.getSingleResult();
    }

    public void deleteByUsers(Usuario emisor, Usuario receptor){
        getInstancia().eliminar(findByUsers(emisor,receptor));
    }
}
