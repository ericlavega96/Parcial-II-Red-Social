package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Pais implements Serializable {
    @Id
    @GeneratedValue
    private long idPais;
    @NotNull
    @Column(unique = true)
    private String pais;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pais", cascade = CascadeType.ALL)
    private Set<Ciudad> ciudades;

    public Pais() {
    }

    public Pais(String pais) {
        this.pais = pais;
    }

    public long getIdPais() {
        return idPais;
    }

    public void setIdPais(long idPais) {
        this.idPais = idPais;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Set<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(Set<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
}
