package entidades;

import logical.LikePost;
import logical.Post;
import logical.Usuario;
import servicios.MetodosDB;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ServiciosLikePost extends MetodosDB<LikePost> {
    private static ServiciosLikePost instancia;

    private ServiciosLikePost(){super(LikePost.class);}

    public static ServiciosLikePost getInstancia(){
        if(instancia==null){
            instancia = new ServiciosLikePost();
        }
        return instancia;
    }
    public void deleteLike(Post post, Usuario user){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select la from LikePost la where la.usuario = :user AND la.post = :post");
        query.setParameter("user", user);
        query.setParameter("post", post);
        List<LikePost> resultado = query.getResultList();
        for(LikePost la : resultado) {
            la.setPost(null);
            la.setUsuario(null);
            getInstancia().editar(la);
            getInstancia().eliminar(la.getId());
        }
        return;
    }
}
