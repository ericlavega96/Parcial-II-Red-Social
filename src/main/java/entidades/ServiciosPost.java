package entidades;


import logical.Post;
import logical.Usuario;
import servicios.MetodosDB;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ServiciosPost extends MetodosDB<Post> {

    private static ServiciosPost instancia;

    private ServiciosPost(){super(Post.class);}

    public static ServiciosPost getInstancia(){
        if(instancia==null){
            instancia = new ServiciosPost();
        }
        return instancia;
    }

    public List<Post> findByAuthor(Usuario user){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select p from Post p where p.autor = :user");
        query.setParameter("user", user);
        List<Post> resultado = query.getResultList();
        return resultado;
    }
}
