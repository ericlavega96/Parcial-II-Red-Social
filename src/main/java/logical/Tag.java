package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue
    private long idTag;

    @NotNull
    private String tag;

    @ManyToMany(mappedBy = "listaEtiquetas")
    private Set<Post> listaTags;

    public Tag() {
    }

    public Tag(String tag) {
        this.tag = tag;
    }

    public long getIdTag() {
        return idTag;
    }

    public void setIdTag(long idTag) {
        this.idTag = idTag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Set<Post> getListaTags() {
        return listaTags;
    }

    public void setListaTags(Set<Post> listaTags) {
        this.listaTags = listaTags;
    }
}
