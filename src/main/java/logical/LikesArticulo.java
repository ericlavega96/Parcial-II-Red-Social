package logical;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class LikesArticulo implements Serializable {
    @Id
    @GeneratedValue
    @Column(name="LIKESARTICULO_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "idPost")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @Column(name="isLIKE")
    boolean isLike;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL)
    Set<LikesArticulo> likeArticulo = new HashSet<>();

    public LikesArticulo() {
    }

    public LikesArticulo(Post post, Usuario usuario, boolean isLike) {
        this.post = post;
        this.usuario = usuario;
        this.isLike = isLike;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public void setLike(boolean isLike) {
        this.isLike = isLike;
    }
}