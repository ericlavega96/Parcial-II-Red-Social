package entidades;

import logical.*;
import servicios.MetodosDB;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ServiciosAlbum extends MetodosDB<Album> {

    private static ServiciosAlbum instancia;

    private ServiciosAlbum(){super(Album.class);}

    public static ServiciosAlbum getInstancia(){
        if(instancia==null){
            instancia = new ServiciosAlbum();
        }
        return instancia;
    }

    public List<Album> findAlbumsByUser(Usuario usuario){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select a from Album a WHERE a.usuario = :usuario order by a.fecha");
        query.setParameter("usuario",usuario);
        List<Album> resultado = query.getResultList();
        return resultado;
    }

    public List<Imagen> getImagenesOrdenadas(Album album){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select i from Album a JOIN a.imagenes i WHERE a.idAlbum = :album order by i.fechaPublicacion DESC");
        query.setParameter("album", album.getIdAlbum());
        return query.getResultList();
    }
}
