package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
public class ComentarioFoto {
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
    @JoinColumn(name = "idImagen")
    @NotNull
    private Imagen imagen;

    public ComentarioFoto() {
    }

    public ComentarioFoto(String comentario, Usuario autor, Imagen imagen) {
        this.comentario = comentario;
        this.autor = autor;
        this.imagen = imagen;
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

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }
}
