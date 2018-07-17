package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Ciudad implements Serializable {
    @Id
    private long idCiudad;
    @NotNull
    @Column(unique = true)
    private String ciudad;
    @ManyToOne
    @JoinColumn(name = "idPais")
    @NotNull
    private Pais pais;

    public Ciudad() {
    }

    public Ciudad(long idCiudad, String ciudad, Pais pais) {
        this.idCiudad = idCiudad;
        this.ciudad = ciudad;
        this.pais = pais;
    }

    public long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
