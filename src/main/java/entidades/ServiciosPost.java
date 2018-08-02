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
        Query query = em.createQuery("select p from Post p where p.autor = :user order by p.fecha DESC" );
        query.setParameter("user", user);
        List<Post> resultado = query.getResultList();
        return resultado;
    }

    public long getLikesCount(Post post){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select l from LikePost l WHERE l.post = :post AND l.isLike = true");
        query.setParameter("post", post);
        long resultado = query.getResultList().size();
        return resultado;
    }

    public boolean findUserLike(Usuario user,Post post){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select l from LikePost l WHERE l.post = :post AND l.usuario = :user");
        query.setParameter("post", post);
        query.setParameter("user",user);
        boolean resultado = query.getResultList().size() >= 1;
        return resultado;
    }
}
