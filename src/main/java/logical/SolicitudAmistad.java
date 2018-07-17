package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
public class SolicitudAmistad {
    @Id
    @GeneratedValue
    private long idSolicitud;

    @ManyToOne
    @JoinColumn(name = "EMISOR")
    @NotNull
    private Usuario emisor;

    @ManyToOne
    @JoinColumn(name = "RECEPTOR")
    @NotNull
    private Usuario receptor;
    private String mensaje;

    public SolicitudAmistad() {
    }

    public long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public Usuario getReceptor() {
        return receptor;
    }

    public void setReceptor(Usuario receptor) {
        this.receptor = receptor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
