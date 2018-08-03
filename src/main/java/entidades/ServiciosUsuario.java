package entidades;

import encapsulaciones.Amigo;
import logical.*;
import servicios.JsonTransformer;
import servicios.MetodosDB;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

public class ServiciosUsuario extends MetodosDB<Usuario> {

    private static ServiciosUsuario instancia;

    private ServiciosUsuario(){super(Usuario.class);}

    public static ServiciosUsuario getInstancia(){
        if(instancia==null){
            instancia = new ServiciosUsuario();
        }
        return instancia;
    }

    public Usuario findByEmailAndPassword(String correo, String password){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select u from Usuario u where u.correo = :correo AND u.password = :password");
        query.setParameter("correo", correo);
        query.setParameter("password", password);
        Usuario resultado = (Usuario)query.getSingleResult();
        return resultado;

    }

    public Usuario findByEmail(String correo){
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select u from Usuario u where u.correo = :correo");
            query.setParameter("correo", correo);
            return (Usuario) query.getSingleResult();
        }catch (Exception ex){
            return null;
        }
    }

    public boolean existAdmin(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select u from Usuario u where u.admin = true");
        if(query.getResultList().size() == 0)
            return false;
        else
            return true;

    }

    public boolean crearAdmin(){
        if(getInstancia().existAdmin())
            return false;
        else{
            Pais pais = ServiciosPais.getInstancia().find("DO");
            getInstancia().crear(new Usuario("Admin", "","admin@redsocial.com", "1234", "NA", new Date(),
                    pais,"Santiago","",
                    "", null, true));
            return true;
        }
    }

    public Set<Usuario> getAmigosTexto(String texto){
        Set<Usuario> resultado = new HashSet<>();
        Usuario user;
        for(String s : texto.split(" "))
            if(s.contains("*") && s.length()>1) {
                user = instancia.findByEmail(s.substring(1));
                if(user != null)
                    resultado.add(user);
            }
        if(resultado.size() > 0)
            return resultado;
        else
            return null;
    }

    public List<Amigo> amigosToJSON(Usuario user){
        List<Amigo> amigos = new ArrayList<>();
        for(Usuario amigo : user.getAmigos())
            amigos.add(new Amigo(amigo.getIdUsuario(),
                    amigo.getCorreo(), amigo.getNombres(), amigo.getApellidos()));
        //return new JsonTransformer().render(amigos);
        return amigos;
    }

    public boolean crearAmigoAdmin() {
        if (getInstancia().findByEmail("amigo@mail.com") == null) {
            System.out.println("prueba");
            Usuario amigo = new Usuario("Amigo", "Amiguito", "amigo@mail.com", "123",
                    "NA", new Date(), ServiciosPais.getInstancia().find("DO"), "Santiago",
                    "", "", null, false);
            Usuario admin = getInstancia().findByEmail("admin@redsocial.com");

            admin.getAmigos().add(amigo);
            amigo.getAmigos().add(admin);
            getInstancia().editar(admin);
            //getInstancia().crear(amigo);
            return true;
        }
        else
            return false;
    }

    public List<Usuario> findNoAmigos(Usuario user, List<Usuario> candidatos){
        List<Usuario> resultado = new ArrayList<>();
        boolean aux = false;
        if(user.getAmigos().size() > 0){
            for(Usuario u : candidatos) {
                for (Usuario a : user.getAmigos()) {
                    if ((u.getCorreo().equals(a.getCorreo())) || u.getCorreo().equals(user.getCorreo())) {
                        aux = true;
                        System.out.println("entro1 " + !u.getCorreo().equals(a.getCorreo()));
                    }
                }
                if(!aux)
                    resultado.add(u);
                aux=false;
            }
        }
        else {
            for (Usuario u : getInstancia().findAll())
                if (!u.getCorreo().equals(user.getCorreo())) {
                    resultado.add(u);
                    System.out.println("entro2");
                }
        }

        return resultado;
    }

    public boolean deleteAmistad(Usuario usuario, Usuario amigo){
       for(Usuario a : usuario.getAmigos())
            if(a.getIdUsuario() == amigo.getIdUsuario()) {
                usuario.getAmigos().remove(a);
                getInstancia().editar(usuario);
                return true;
            }
       return false;
    }

    public List<Usuario> findSugerencia(Usuario user){
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("select u from Usuario u where u.idUsuario <> :userID AND" +
                    " (u.empleo = :empleo OR (u.ciudad = :ciudad AND u.pais = :pais) OR u.lugarDeEstudio = :estudio)");
            query.setParameter("userID", user.getIdUsuario());
            query.setParameter("empleo", user.getEmpleo());
            query.setParameter("ciudad", user.getCiudad());
            query.setParameter("pais", user.getPais());
            query.setParameter("estudio", user.getLugarDeEstudio());
            return query.getResultList();
        }catch (Exception ex){
            return null;
        }
    }


}
