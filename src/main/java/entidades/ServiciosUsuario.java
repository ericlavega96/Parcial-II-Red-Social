package entidades;

import logical.*;
import servicios.MetodosDB;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;

public class ServiciosUsuario extends MetodosDB<Usuario> {

    private static ServiciosUsuario instancia;

    private ServiciosUsuario(){super(Usuario.class);}

    public static ServiciosUsuario getInstancia(){
        if(instancia==null){
            instancia = new ServiciosUsuario();
        }
        return instancia;
    }

    public Usuario findByEmailAndPassword(String correo, String password){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select u from Usuario u where u.correo = :correo AND u.password = :password");
        query.setParameter("correo", correo);
        query.setParameter("password", password);
        Usuario resultado = (Usuario)query.getSingleResult();
        return resultado;

    }

    public boolean existAdmin(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select u from Usuario u where u.admin = true");
        if(query.getResultList().size() == 0)
            return false;
        else
            return true;

    }

    public boolean crearAdmin(){
        if(getInstancia().existAdmin())
            return false;
        else{
            Pais pais = new Pais("RD");
            Ciudad ciudad = new Ciudad("Santiago", pais);
            ServiciosPais.getInstancia().crear(pais);
            ServiciosCiudad.getInstancia().crear(ciudad);
            getInstancia().crear(new Usuario("Administrador", "" ,"", new Date(), ciudad,"" , "","admin@redsocial.com","1234", null, true));
            return true;
        }
    }
}
