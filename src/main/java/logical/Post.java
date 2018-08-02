package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class    Post {
    @Id
    @GeneratedValue
    private long idPost;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    @NotNull
    private Usuario autor;

    @OneToOne(cascade = CascadeType.ALL)
    private Imagen fotoPost;

    @NotNull
    private String cuerpo;

    @NotNull
    private Date fecha;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ComentarioPost> listaComentariosPost;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "POST_TAG", joinColumns = { @JoinColumn(name = "idPost") }, inverseJoinColumns = {
            @JoinColumn(name = "idTag") })
    private Set<Tag> listaTags;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    Set<LikePost> likePosts = new HashSet<>();

    public Post() {
    }

    public Post(Usuario autor, Imagen fotoPost, String cuerpo, Date fecha, Set<ComentarioPost> listaComentariosPost, Set<Tag> listaTags, Set<LikePost> likePosts) {
        this.autor = autor;
        this.fotoPost = fotoPost;
        this.cuerpo = cuerpo;
        this.fecha = fecha;
        this.listaComentariosPost = listaComentariosPost;
        this.listaTags = listaTags;
        this.likePosts = likePosts;
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
}
