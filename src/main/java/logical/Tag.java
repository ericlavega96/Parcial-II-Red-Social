package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue
    private long idTag;

    @NotNull
    private String tag;

    @ManyToMany(mappedBy = "listaTags")
    private Set<Post> listaPost;

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

    public Set<Post> getlistaPost() {
        return listaPost;
    }

    public void setlistaPost(Set<Post> listaPost) {
        this.listaPost = listaPost;
    }

    public static Set<Tag> crearEtiquetas(String[] tags){
        Set<Tag> etiquetasList = new HashSet<>();
        for (String etiqueta : tags)
            etiquetasList.add(new Tag(etiqueta.trim()));
        return etiquetasList;
    }
}
