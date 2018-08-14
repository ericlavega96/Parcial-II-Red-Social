package encapsulaciones;

import java.util.Date;

public class Post {
    private long id;
    private Usuario autor;
    private Imagen imagen;
    private String cuerpo;
    private Date fecha;

    public Post() {
    }

    public Post(long id, Usuario autor, Imagen imagen, String cuerpo, Date fecha) {
        this.id = id;
        this.autor = autor;
        this.imagen = imagen;
        this.cuerpo = cuerpo;
        this.fecha = fecha;
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
}
