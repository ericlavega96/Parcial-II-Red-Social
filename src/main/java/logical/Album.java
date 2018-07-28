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

    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    Set<LikeAlbum> likeAlbum = new HashSet<>();

    public Album() {
    }

    public Album(Set<Imagen> imagenes, Usuario usuario, String nombre, String descripcion) {
        this.imagenes = imagenes;
        this.usuario = usuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<LikeAlbum> getLikeAlbum() {
        return likeAlbum;
    }

    public void setLikeAlbum(Set<LikeAlbum> likeAlbum) {
        this.likeAlbum = likeAlbum;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
