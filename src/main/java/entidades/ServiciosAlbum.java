package entidades;

import logical.Album;
import logical.Pais;
import logical.Usuario;
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
}
