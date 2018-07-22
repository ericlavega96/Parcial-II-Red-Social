package entidades;

import logical.Ciudad;
import logical.Imagen;
import logical.Usuario;
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

    public Usuario findByUsernameAndPassword(String username, String password){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select u from Usuario u where u.correo = :username AND u.password = :password");
        query.setParameter("username", username);
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
            getInstancia().crear(new Usuario("admin@redsocial.com", "Admin", "", "NA", new Date(), new Ciudad(),"",
                    "", null, true));
            return true;
        }
    }
}
