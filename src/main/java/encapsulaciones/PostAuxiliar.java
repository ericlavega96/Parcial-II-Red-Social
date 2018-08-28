package encapsulaciones;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root
public class PostAuxiliar {
    @Element
    private String correo;
    @Element
    private String password;
    @Element
    private String imagen;
    @Element
    private String cuerpo;
    @Element
    private String tags;
    @Element
    private boolean privado;


    public PostAuxiliar() {

    }

    public PostAuxiliar(String correo, String password, String imagen, String cuerpo, String tags, boolean privado) {
        this.correo = correo;
        this.password = password;
        this.imagen = imagen;
        this.cuerpo = cuerpo;
        this.tags = tags;
        this.privado = privado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String usuario) {
        this.correo = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isPrivado() {
        return privado;
    }

    public void setPrivado(boolean privado) {
        this.privado = privado;
    }
}