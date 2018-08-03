package entidades;

import logical.*;
import servicios.MetodosDB;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ServiciosLikeImagen extends MetodosDB<LikeImagen> {
    private static ServiciosLikeImagen instancia;

    private ServiciosLikeImagen(){super(LikeImagen.class);}

    public static ServiciosLikeImagen getInstancia(){
        if(instancia==null){
            instancia = new ServiciosLikeImagen();
        }
        return instancia;
    }
    public void deleteLike(Imagen imagen, Usuario user){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select li from LikeImagen li where li.usuario = :user AND li.imagen = :imagen");
        query.setParameter("user", user);
        query.setParameter("imagen", imagen);
        List<LikeImagen> resultado = query.getResultList();
        for(LikeImagen li : resultado) {
            li.setImagen(null);
            li.setUsuario(null);
            getInstancia().editar(li);
            getInstancia().eliminar(li.getId());
        }
        return;
    }
}
