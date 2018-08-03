package logical;

import javax.persistence.*;

@Entity
public class LikeImagen {
    @Id
    @GeneratedValue
    @Column(name="LIKESIMAGEN_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "idImagen")
    private Imagen imagen;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    public LikeImagen() {
    }

    public LikeImagen(Imagen imagen, Usuario usuario) {
        this.imagen = imagen;
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
