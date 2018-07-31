package entidades;

import logical.Imagen;
import servicios.MetodosDB;
import spark.Request;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


public class ServiciosImagen extends MetodosDB<Imagen> {

    private static ServiciosImagen instancia;

    private ServiciosImagen(){super(Imagen.class);}

    public static ServiciosImagen getInstancia(){
        if(instancia==null){
            instancia = new ServiciosImagen();
        }
        return instancia;
    }

    public String guardarFoto(String attName,Request req) {
        File fotosDir = new File("src/main/resources/templates/photos");
        fotosDir.mkdir();
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile(fotosDir.toPath(), "", "");
            req.attribute("org.eclipse.multipartConfig", new MultipartConfigElement("/temp"));
        } catch (IOException e) {
            return "Error " + e.toString();
        }
        try {
            InputStream input = req.raw().getPart(attName).getInputStream();
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return "Error " + e.toString();
        } catch (ServletException e) {
            return "Error " + e.toString();
        }
        return "/" + tempFile.getFileName().toString();
    }
}
