package encapsulaciones;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Usuario {
    @Element
    private String correo;
    @Element
    private String password;
    @Element
    private String nombres;
    @Element
    private String apellidos;

    public Usuario() {
    }

    public Usuario(String correo, String password, String nombres, String apellidos) {
        this.correo = correo;
        this.password = password;
        this.nombres = nombres;
        this.apellidos = apellidos;
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
