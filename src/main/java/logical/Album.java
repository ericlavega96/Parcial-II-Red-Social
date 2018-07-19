package logical;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Album {
    @Id
    @GeneratedValue
    private long idAlbum;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "album")
    private Set<Imagen> imagenes;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    public Album() {
    }

    public long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Set<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(Set<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
