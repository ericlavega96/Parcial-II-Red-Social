package logical;

import javax.persistence.*;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "album",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ComentarioAlbum> listaComentarioAlbum;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    Set<LikeAlbum> likeAlbum = new HashSet<>();

    public Album() {
    }

    public Album(Set<Imagen> imagenes, Usuario usuario) {
        this.imagenes = imagenes;
        this.usuario = usuario;
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
