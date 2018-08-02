package encapsulaciones;


public class Amigo {

    private long codigo;
    private String correo;
    private String nombres;
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
