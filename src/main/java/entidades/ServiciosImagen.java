package entidades;

import logical.Imagen;
import servicios.MetodosDB;
import spark.Request;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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
        Path tempFile = null;
        tempFile = Files.createTempFile(fotosDir.toPath(), "", ".png");
        req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        try(InputStream input = req.raw().getPart(attName).getInputStream()){
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }
        return tempFile.getFileName().toString();
    }

    public long getLikesCount(Imagen imagen){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select i from LikeImagen i WHERE i.imagen = :imagen AND i.isLike = true");
        query.setParameter("imagen", imagen);
        long resultado = query.getResultList().size();
        return resultado;
    }

}
