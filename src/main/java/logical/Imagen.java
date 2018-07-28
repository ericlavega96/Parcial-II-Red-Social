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

    @OneToMany(mappedBy = "album",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ComentarioFoto> listaComentarioFoto;


}
