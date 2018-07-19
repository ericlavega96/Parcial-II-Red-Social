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


    //Cambiar a uno a uno
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "fotoPerfil")
    private Set<Usuario> usuariosPerfil;

    @ManyToOne
    @JoinColumn(name = "album")
    private Album album;

    //Cambiar a many to many
    @ManyToOne
    @JoinColumn(name = "album")
    private Set<Usuario> listaUsuariosEtiquetados;

}
