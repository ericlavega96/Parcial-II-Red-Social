package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Pais implements Serializable {
    @Id
    private long idPais;
    @NotNull
    @Column(unique = true)
    private String pais;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "pais")
    private Set<Ciudad> ciudades;
}
