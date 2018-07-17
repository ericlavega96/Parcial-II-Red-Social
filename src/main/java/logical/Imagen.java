package logical;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Imagen {
    @Id
    private long idImagen;
    @Column(length = 500000000)
    private byte [] imagen;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "fotoPerfil")
    private Set<Usuario> usuariosPerfil;

    @ManyToOne
    @JoinColumn(name = "album")
    private Album album;

    @ManyToMany(mappedBy = "listaImagenesEtiquetadas")
    private Set<Usuario> listaUsuariosEtiquetados;

}
