package logical;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Imagen {
    @Id
    @GeneratedValue
    private long idImagen;
    @Column(length = 500000000)
    private byte [] imagen;

    @ManyToOne
    @JoinColumn(name = "album")
    private Album album;

    @ManyToMany(mappedBy = "listaImagenesEtiquetadas")
    private Set<Usuario> listaUsuariosEtiquetados;

    @OneToMany(mappedBy = "imagen",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ComentarioFoto> listaComentarioFoto;

    public Imagen(byte[] imagen, Album album, Set<Usuario> listaUsuariosEtiquetados) {
        this.imagen = imagen;
        this.album = album;
        this.listaUsuariosEtiquetados = listaUsuariosEtiquetados;
    }

    public long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(long idImagen) {
        this.idImagen = idImagen;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Set<Usuario> getListaUsuariosEtiquetados() {
        return listaUsuariosEtiquetados;
    }

    public void setListaUsuariosEtiquetados(Set<Usuario> listaUsuariosEtiquetados) {
        this.listaUsuariosEtiquetados = listaUsuariosEtiquetados;
    }

    public Set<ComentarioFoto> getListaComentarioFoto() {
        return listaComentarioFoto;
    }

    public void setListaComentarioFoto(Set<ComentarioFoto> listaComentarioFoto) {
        this.listaComentarioFoto = listaComentarioFoto;
    }
}
