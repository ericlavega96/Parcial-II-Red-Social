package main;

import entidades.ServiciosUsuario;
import servicios.ServiciosBootStrap;

import javax.persistence.EntityManager;

import static spark.Spark.staticFiles;

public class Main {
    private static EntityManager em;

    public static void main(String[] args) throws Exception{
        staticFiles.location("/templates");
        new RutasSpark().iniciarSpark();
        ServiciosBootStrap.getInstancia().init();
        ServiciosUsuario.getInstancia().crearAdmin();
    }
}
