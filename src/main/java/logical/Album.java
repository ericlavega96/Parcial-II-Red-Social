package logical;

import entidades.ServiciosAlbum;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Album {
    @Id
    @GeneratedValue
    private long idAlbum;

    private String nombre;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "album",cascade = CascadeType.ALL)
    private Set<Imagen> imagenes;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    private Date fecha;

    private String descripcion;

    public Album() {
    }

    public Album(String nombre, String descripcion,Usuario usuario,Date fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenes = new HashSet<>();
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Set<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(Set<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Imagen> getImagenesOrdenadas(){
        return ServiciosAlbum.getInstancia().getImagenesOrdenadas(this);
    }
    public String getNombreLimit(){
        String resultado = "";
        int max= 28;
        if(nombre.length()>max)
            resultado += nombre.substring(0,max-4) + "...";
        else resultado= nombre;
        return resultado;
    }
}
