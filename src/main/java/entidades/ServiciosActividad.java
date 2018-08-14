package entidades;

import logical.Actividad;
import logical.Usuario;
import servicios.MetodosDB;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ServiciosActividad extends MetodosDB<Actividad> {

    private static ServiciosActividad instancia;

    private ServiciosActividad(){super(Actividad.class);}

    public static ServiciosActividad getInstancia(){
        if(instancia==null){
            instancia = new ServiciosActividad();
        }
        return instancia;
    }

    public List<Actividad> findByUser(Usuario user){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select a from Actividad a where a.usuario = :user order by a.fechaActividad DESC" );
        query.setParameter("user", user);
        List<Actividad> resultado = query.getResultList();
        return resultado;
    }

    public List<Actividad> actividadesAmigos(Usuario user){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select ac from Usuario u, Actividad ac JOIN u.amigos a WHERE u.correo = :correo AND (ac.usuario = a OR ac.usuario = u) order by ac.fechaActividad DESC");
        query.setParameter("correo", user.getCorreo());
        return query.getResultList();
    }
}
