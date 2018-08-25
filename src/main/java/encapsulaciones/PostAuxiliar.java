package encapsulaciones;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class PostAuxiliar {
    @Element
    String correo;
    @Element
    String password;
    @Element
    String cuerpo;
    @Element
    String imagen;
    @Element
    String tags;

    public PostAuxiliar() {
    }

    public PostAuxiliar(String correo, String password, String cuerpo, String imagen, String tags) {
        this.correo = correo;
        this.password = password;
        this.cuerpo = cuerpo;
        this.imagen = imagen;
        this.tags = tags;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
