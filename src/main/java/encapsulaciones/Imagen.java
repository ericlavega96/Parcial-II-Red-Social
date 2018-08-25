package encapsulaciones;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Imagen {
    @Element
    private String imagen;

    public Imagen() {
    }

    public Imagen(String imagen) {
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
