package main;

import entidades.ServiciosPais;
import entidades.ServiciosUsuario;
import rest.RestMain;
import servicios.ServiciosBootStrap;
import soap.SoapArranque;

import javax.persistence.EntityManager;

import static spark.Spark.staticFiles;

public class Main {
    private static EntityManager em;

    public static void main(String[] args) throws Exception{
        staticFiles.location("/templates");
        staticFiles.externalLocation("photos");
        ServiciosBootStrap.getInstancia().init();
        ServiciosPais.getInstancia().crearPaises();
        ServiciosUsuario.getInstancia().crearAdmin();
        //ServiciosUsuario.getInstancia().crearAmigoAdmin();
        SoapArranque.init();
        new RutasSpark().iniciarSpark();
        new RestMain().iniciarServicioRest();
        new Filtros().aplicarFiltros();
    }


}
