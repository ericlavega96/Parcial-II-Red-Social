package entidades;

import logical.Pais;
import servicios.MetodosDB;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ServiciosPais extends MetodosDB<Pais> {

    private static ServiciosPais instancia;

    private ServiciosPais(){super(Pais.class);}

    public static ServiciosPais getInstancia(){
        if(instancia==null){
            instancia = new ServiciosPais();
        }
        return instancia;
    }

    private static boolean existPais(){
        List<Pais> paisesEncontrados =getInstancia().findAll();
        if(paisesEncontrados.size() <= 0)
            return false;
        else
            return true;
    }

    public static boolean crearPaises(){

        if(!existPais()){
            Locale localObj;
            try {
                for (String p : Locale.getISOCountries()) {
                    localObj = new Locale("", p);
                    //System.out.println(" - Código de país: " + p + " para el país " + localObj.getDisplayCountry());
                    getInstancia().crear(new Pais(p, localObj.getDisplayCountry()));
                }
                return true;
            }catch (Exception ex){
                throw  ex;
            }
        }
        else
            return false;
    }

    public Pais findByCountry(String pais){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select p from Pais p where p.pais = :pais");
        query.setParameter("pais", pais);

        Pais resultado = (Pais)query.getSingleResult();
        return resultado;
    }

    public List<Pais> findAllOrdenado(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select p from Pais p order by p.pais");

        List<Pais> resultado = query.getResultList();
        return resultado;
    }




}
