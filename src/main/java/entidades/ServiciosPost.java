package entidades;


import logical.ComentarioPost;
import logical.Post;
import logical.Usuario;
import servicios.MetodosDB;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
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

    public List<Post> postAmigosUser(Usuario user){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select p from Usuario u, Post p JOIN u.amigos a WHERE u.correo = :correo AND p.autor = a order by p.fecha DESC");
        query.setParameter("correo", user.getCorreo());
        return query.getResultList();
    }

    public List<ComentarioPost> getComentariosOrdenados(Post post){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select c from Post p JOIN p.listaComentariosPost c WHERE p.idPost = :post order by c.fecha ASC");
        query.setParameter("post", post.getIdPost());
        return query.getResultList();
    }

    public List<Post> findPostPrivados(Usuario user, Usuario amigo){
        List<Post> posts = new ArrayList<>();
        for(Post post : amigo.getPosts()) {
            if (isAmigo(user, amigo) && post.isEsPrivado())
                posts.add(post);
            else if(!post.isEsPrivado())
                posts.add(post);
        }
        return posts;

    }

    private boolean isAmigo(Usuario user, Usuario amigo){

        for(Usuario a : user.getAmigos())
            if(a.getIdUsuario() == amigo.getIdUsuario())
                return true;
        return false;
    }
}
