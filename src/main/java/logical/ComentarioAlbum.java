package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
public class ComentarioAlbum {
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
    @JoinColumn(name = "idAlbum")
    @NotNull
    private Album album;

    public ComentarioAlbum() {
    }

    public ComentarioAlbum(String comentario, Usuario autor, Album album) {
        this.comentario = comentario;
        this.autor = autor;
        this.album = album;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }
}
