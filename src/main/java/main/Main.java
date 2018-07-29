package main;

import entidades.ServiciosPais;
import entidades.ServiciosUsuario;
import org.jasypt.util.text.BasicTextEncryptor;
import servicios.ServiciosBootStrap;

import javax.persistence.EntityManager;

import static spark.Spark.staticFiles;

public class Main {
    private static EntityManager em;

    public static void main(String[] args) throws Exception{
        staticFiles.location("/templates");
        ServiciosBootStrap.getInstancia().init();
        ServiciosPais.getInstancia().crearPaises();
        ServiciosUsuario.getInstancia().crearAdmin();
        new RutasSpark().iniciarSpark();
    }

    public static String Encryptamiento(String text){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray("fjkldjsklfjsaklj1234".toCharArray());
        String myEncryptedText = textEncryptor.encrypt(text);
        return myEncryptedText;
    }
}
