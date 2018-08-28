package logical;

import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ComentarioPost {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String comentario;

    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    @NotNull
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "idPost")
    @NotNull
    private Post post;

    public ComentarioPost() {
    }

    public ComentarioPost(String comentario,Date fecha,Usuario autor, Post post) {
        this.comentario = comentario;
        this.fecha = fecha;
        this.autor = autor;
        this.post = post;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
