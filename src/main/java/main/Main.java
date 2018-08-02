package main;

import entidades.ServiciosPais;
import entidades.ServiciosUsuario;
import servicios.ServiciosBootStrap;

import javax.persistence.EntityManager;

import java.io.File;

import static spark.Spark.staticFiles;

public class Main {
    private static EntityManager em;

    public static void main(String[] args) throws Exception{
        staticFiles.location("/templates");
        staticFiles.externalLocation("photos");
        ServiciosBootStrap.getInstancia().init();
        ServiciosPais.getInstancia().crearPaises();
        ServiciosUsuario.getInstancia().crearAdmin();
        ServiciosUsuario.getInstancia().crearAmigoAdmin();
        new RutasSpark().iniciarSpark();
        new Filtros().aplicarFiltros();
    }


}
