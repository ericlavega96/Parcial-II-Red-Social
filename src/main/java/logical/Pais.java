package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Pais implements Serializable {
    @Id
    private String idPais;
    //@NotNull
    //@Column(unique = true)
    private String pais;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pais",cascade = CascadeType.ALL)
    private Set<Usuario> usuarios;

    public Pais() {
    }

    public Pais(String idPais, String pais) {
        this.idPais = idPais;
        this.pais = pais;
    }

    public String getIdPais() {
        return idPais;
    }

    public void setIdPais(String idPais) {
        this.idPais = idPais;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Set<Usuario> getCiudades() {
        return usuarios;
    }

    public void setCiudades(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
