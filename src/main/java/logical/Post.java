package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
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

    @NotNull
    private String cuerpo;

    @NotNull
    private Date fecha;

    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Comentario> listaComentarios;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "POST_TAG", joinColumns = { @JoinColumn(name = "idPost") }, inverseJoinColumns = {
            @JoinColumn(name = "idTag") })
    private Set<Tag> listaTags;


    @OneToMany(mappedBy = "idPost", cascade = CascadeType.ALL)
    Set<LikePost> likePosts = new HashSet<>();

    public Post() {
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

    public Set<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(Set<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public Set<Tag> getListaTags() {
        return listaTags;
    }

    public void setListaTags(Set<Tag> listaTags) {
        this.listaTags = listaTags;
    }
}
