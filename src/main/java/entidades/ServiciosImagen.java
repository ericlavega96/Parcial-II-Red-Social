package entidades;

import logical.ComentarioFoto;
import logical.Imagen;
import logical.Usuario;
import servicios.MetodosDB;
import spark.Request;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;

import static spark.Spark.staticFiles;


public class ServiciosImagen extends MetodosDB<Imagen> {

    private static ServiciosImagen instancia;

    private ServiciosImagen(){super(Imagen.class);}

    public static ServiciosImagen getInstancia(){
        if(instancia==null){
            instancia = new ServiciosImagen();
        }
        return instancia;
    }

    public String guardarFoto(String attName,File fotosDir,Request req) throws IOException, ServletException {
        req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        Path tempFile= null;
        String ruta = null;
        Part imagen = req.raw().getPart(attName);
        if(imagen.getSize() > 0){
            try(InputStream input = imagen.getInputStream()){
                tempFile = Files.createTempFile(fotosDir.toPath(), "", ".png");
                Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
                ruta = tempFile.getFileName().toString();
            }
        }
        return ruta;
    }

    public long getLikesCount(Imagen imagen){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select i from LikeImagen i WHERE i.imagen = :imagen AND i.isLike = true");
        query.setParameter("imagen", imagen);
        long resultado = query.getResultList().size();
        return resultado;
    }

    public boolean findUserLike(Usuario user, Imagen imagen){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select l from LikeImagen l WHERE l.imagen = :imagen AND l.usuario = :user");
        query.setParameter("imagen", imagen);
        query.setParameter("user",user);
        boolean resultado = query.getResultList().size() >= 1;
        return resultado;
    }

    public List<ComentarioFoto> getComentariosOrdenados(Imagen imagen){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select c from Imagen i JOIN i.listaComentarioFoto c WHERE i.idImagen = :imagen order by c.fecha ASC");
        query.setParameter("imagen", imagen.getIdImagen());
        return query.getResultList();
    }

    public String guardarFotoBase64(String base64Img) throws IOException {
        Path tempFile= null;
        String ruta = null;
        Path fotosDir = Paths.get("photos");
        byte[] decodedImg = Base64.getDecoder().decode(base64Img);
        if(decodedImg.length > 0){
            ByteArrayInputStream bis = new ByteArrayInputStream(decodedImg);
            tempFile = Files.createTempFile(fotosDir, "", ".png");
            Files.copy(bis, tempFile, StandardCopyOption.REPLACE_EXISTING);
            ruta = tempFile.getFileName().toString();
        }
        return ruta;
    }

}
