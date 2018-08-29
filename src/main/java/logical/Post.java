package logical;

import javax.validation.constraints.NotNull;
import entidades.ServiciosPost;
import entidades.ServiciosUsuario;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private long idPost;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    @NotNull
    private Usuario autor;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Imagen fotoPost;

    @NotNull
    private String cuerpo;

    @NotNull
    private Date fecha;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ComentarioPost> listaComentariosPost;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "POST_TAG", joinColumns = { @JoinColumn(name = "idPost") }, inverseJoinColumns = {
            @JoinColumn(name = "idTag") })
    private Set<Tag> listaTags;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<LikePost> likePosts = new HashSet<>();

    private String geolocation;

    private boolean esPrivado;

    public Post() {
    }

    public Post(Usuario autor, Imagen fotoPost, String cuerpo, Date fecha, Set<ComentarioPost> listaComentariosPost, Set<Tag> listaTags, Set<LikePost> likePosts, String geolocation,boolean esPrivado) {
        this.autor = autor;
        this.fotoPost = fotoPost;
        this.cuerpo = cuerpo;
        this.fecha = fecha;
        this.listaComentariosPost = listaComentariosPost;
        this.listaTags = listaTags;
        this.likePosts = likePosts;
        this.geolocation = geolocation;
        this.esPrivado = esPrivado;
    }

    public Post(Usuario autor, Imagen fotoPost, String cuerpo, Date fecha, Set<ComentarioPost> listaComentariosPost, Set<Tag> listaTags, Set<LikePost> likePosts,boolean esPrivado) {
        this.autor = autor;
        this.fotoPost = fotoPost;
        this.cuerpo = cuerpo;
        this.fecha = fecha;
        this.listaComentariosPost = listaComentariosPost;
        this.listaTags = listaTags;
        this.likePosts = likePosts;
        this.geolocation = "";
        this.esPrivado = esPrivado;
    }

    public long getIdPost() {
        return idPost;
    }

    public void setIdPost(long idPost) {
        this.idPost = idPost;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Set<ComentarioPost> getListaComentariosPost() {
        return listaComentariosPost;
    }

    public void setListaComentariosPost(Set<ComentarioPost> listaComentariosPost) {
        this.listaComentariosPost = listaComentariosPost;
    }

    public Set<Tag> getListaTags() {
        return listaTags;
    }

    public void setListaTags(Set<Tag> listaTags) {
        this.listaTags = listaTags;
    }

    public Imagen getFotoPost() {
        return fotoPost;
    }

    public void setFotoPost(Imagen fotoPost) {
        this.fotoPost = fotoPost;
    }

    public Set<LikePost> getLikePosts() {
        return likePosts;
    }

    public void setLikePosts(Set<LikePost> likePosts) {
        this.likePosts = likePosts;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public boolean isEsPrivado() {
        return esPrivado;
    }

    public void setEsPrivado(boolean esPrivado) {
        this.esPrivado = esPrivado;
    }

    public List<ComentarioPost> getComentariosOrdenados(){
        return ServiciosPost.getInstancia().getComentariosOrdenados(this);}

    public long likesCount(){return ServiciosPost.getInstancia().getLikesCount(this);}

    public String reemplazarUsuariosTexto(){
        Set<Usuario> resultado = new HashSet<>();
        Usuario user;
        String nuevoTexto = cuerpo;
        for(String s : nuevoTexto.split(" "))
            if(s.length()>1  && s.substring(0,1).equals("*")) {
                user = ServiciosUsuario.getInstancia().findByEmail(s.substring(1));
                if(user != null)
                    resultado.add(user);
            }
        for (Usuario u: resultado){
            nuevoTexto = nuevoTexto.replace("*" + u.getCorreo(),u.getNombres() + " " + u.getApellidos());
            System.out.println("Correo a reemplazar "  + "*" + u.getCorreo());
        }
        return nuevoTexto;
    }
}
