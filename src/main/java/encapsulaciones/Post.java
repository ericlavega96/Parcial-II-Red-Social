package encapsulaciones;

import java.util.Date;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Post {
    @Element
    private long id;
    @Element
    private Usuario autor;
    @Element
    private Imagen imagen;
    @Element
    private String cuerpo;
    @Element
    private Date fecha;
    @Element
    private String etiquetas;

    public Post(String correo, String password, String cuerpo, String imagen, String tags) {
        this.autor = new Usuario(correo,password,"","");
        this.cuerpo = cuerpo;
        this.imagen = new Imagen(imagen);
        this.etiquetas = tags;
    }

    public Post(long id, Usuario autor, Imagen imagen, String cuerpo, Date fecha ) {
        this.id = id;
        this.autor = autor;
        this.imagen = imagen;
        this.cuerpo = cuerpo;
        this.fecha = fecha;
    }

    public Post(long id, Usuario autor, Imagen imagen, String cuerpo, Date fecha, String etiquetas) {
        this.id = id;
        this.autor = autor;
        this.imagen = imagen;
        this.cuerpo = cuerpo;
        this.fecha = fecha;
        this.etiquetas = etiquetas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }
}
