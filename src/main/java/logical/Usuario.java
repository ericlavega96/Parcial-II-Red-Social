package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
public class Usuario implements Serializable{
    @Id
    private long idUsuario;
    @NotNull
    @Column(unique = true)
    private String correo;
    @NotNull
    private String nombres;
    @NotNull
    private String apellidos;
    @NotNull
    private String sexo;
    @NotNull
    private Date fechaNacimiento;
    @NotNull
    private Ciudad ciudad;
    private String lugarDeEstudio;
    private String empleo;
    @NotNull
    private Set<Post> posts;
    @NotNull
    private Set<Album> albumes;
    @NotNull
    private Actividad timeline;
    @NotNull
    private Set<Usuario> amigos;
    @NotNull
    private Imagen fotoPerfil;
    @NotNull
    private Set<SolicitudAmistad> solicitudes;
    @NotNull
    private boolean admin;

    public Usuario() {
    }
}
