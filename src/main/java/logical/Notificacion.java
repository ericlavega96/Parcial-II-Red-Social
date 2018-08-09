package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Notificacion {
    @Id
    @GeneratedValue
    private long idActividad;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    @NotNull
    private String actividad;

    @NotNull
    private Date fechaActividad;

    public Notificacion() {
    }

    public Notificacion(Usuario usuario, String actividad, Date fechaActividad) {
        this.usuario = usuario;
        this.actividad = actividad;
        this.fechaActividad = fechaActividad;
    }

    public long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(long idActividad) {
        this.idActividad = idActividad;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Date getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Date fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public String getFechaString(){
        return fechaActividad.toString();
    }
}
