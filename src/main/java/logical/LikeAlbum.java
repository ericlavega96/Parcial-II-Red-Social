package logical;

import javax.persistence.*;

@Entity
public class LikeAlbum {
    @Id
    @GeneratedValue
    @Column(name="LIKESARTICULO_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "idAlbum")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @Column(name="isLIKE")
    private boolean isLike;

    public LikeAlbum() {
    }

    public LikeAlbum(Album album, Usuario usuario, boolean isLike) {
        this.album = album;
        this.usuario = usuario;
        this.isLike = isLike;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
