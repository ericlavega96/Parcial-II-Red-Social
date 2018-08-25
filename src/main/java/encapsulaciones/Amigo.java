package encapsulaciones;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Amigo {
    @Element
    private long codigo;
    @Element
    private String correo;
    @Element
    private String nombres;
    @Element
    private String apellidos;

    public Amigo() {
    }

    public Amigo(long codigo, String correo, String nombres, String apellidos) {
        this.codigo = codigo;
        this.correo = correo;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
