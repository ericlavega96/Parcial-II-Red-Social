package logical;

import com.google.gson.annotations.Expose;
import entidades.ServiciosImagen;
import entidades.ServiciosPost;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Imagen {
    @Id
    @GeneratedValue
    private long idImagen;

    private String imagen;

    @ManyToOne
    @JoinColumn(name = "album")
    private Album album;

    @ManyToMany(mappedBy = "listaImagenesEtiquetadas")
    private Set<Usuario> listaUsuariosEtiquetados;

    @OneToMany(mappedBy = "imagen",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ComentarioFoto> listaComentarioFoto;

    public Imagen() {

    }

    public Imagen(String imagen, Album album, Set<Usuario> listaUsuariosEtiquetados) {
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
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

    public long likesCount(){return ServiciosImagen.getInstancia().getLikesCount(this);}
}
