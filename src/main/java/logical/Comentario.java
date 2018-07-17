package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
public class Comentario {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    @NotNull
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "idPost")
    @NotNull
    private Post post;

    public Comentario() {
    }

    public Comentario(String comentario, Usuario autor, Post post) {
        this.comentario = comentario;
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
