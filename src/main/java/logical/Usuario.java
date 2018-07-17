package logical;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Usuario implements Serializable{
    @Id
    @GeneratedValue
    private long idUsuario;
    @NotNull
    @Column(unique = true)
    private String correo;
    @NotNull
    private String nombres;
    @NotNull
    private String apellidos;
    @NotNull
    private String sexo;
    @NotNull
    private Date fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "idCiudad")
    @NotNull
    private Ciudad ciudad;

    private String lugarDeEstudio;
    private String empleo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "autor")
    private Set<Post> posts;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "autor")
    private Set<Comentario> cometarios;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "usuario")
    private Set<Album> albumes;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario")
    private Set<Actividad> timeline;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "AMISTADES", joinColumns = { @JoinColumn(name = "idUsuario") }, inverseJoinColumns = {
            @JoinColumn (name = "idUsuario") })
    private Set<Usuario> amigos;

    @ManyToOne
    @JoinColumn(name = "idImagen")
    @NotNull
    private Imagen fotoPerfil;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "emisor")
    private Set<SolicitudAmistad> solicitudesEnviadas;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "receptor")
    private Set<SolicitudAmistad> solicitudesRecividas;

    @NotNull
    private boolean admin;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USUARIOS_TAGGED", joinColumns = { @JoinColumn(name = "idUsuario") }, inverseJoinColumns = {
            @JoinColumn (name = "IdImagen") })
    private Set<Imagen> listaImagenesEtiquetadas;

    @OneToMany(mappedBy = "usuario",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<LikesArticulo> likeArticulo = new HashSet<>();

    public Usuario() {
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public String getLugarDeEstudio() {
        return lugarDeEstudio;
    }

    public void setLugarDeEstudio(String lugarDeEstudio) {
        this.lugarDeEstudio = lugarDeEstudio;
    }

    public String getEmpleo() {
        return empleo;
    }

    public void setEmpleo(String empleo) {
        this.empleo = empleo;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Comentario> getCometarios() {
        return cometarios;
    }

    public void setCometarios(Set<Comentario> cometarios) {
        this.cometarios = cometarios;
    }

    public Set<Album> getAlbumes() {
        return albumes;
    }

    public void setAlbumes(Set<Album> albumes) {
        this.albumes = albumes;
    }

    public Set<Actividad> getTimeline() {
        return timeline;
    }

    public void setTimeline(Set<Actividad> timeline) {
        this.timeline = timeline;
    }

    public Set<Usuario> getAmigos() {
        return amigos;
    }

    public void setAmigos(Set<Usuario> amigos) {
        this.amigos = amigos;
    }

    public Imagen getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(Imagen fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Set<SolicitudAmistad> getSolicitudesEnviadas() {
        return solicitudesEnviadas;
    }

    public void setSolicitudesEnviadas(Set<SolicitudAmistad> solicitudesEnviadas) {
        this.solicitudesEnviadas = solicitudesEnviadas;
    }

    public Set<SolicitudAmistad> getSolicitudesRecividas() {
        return solicitudesRecividas;
    }

    public void setSolicitudesRecividas(Set<SolicitudAmistad> solicitudesRecividas) {
        this.solicitudesRecividas = solicitudesRecividas;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<Imagen> getListaImagenesEtiquetadas() {
        return listaImagenesEtiquetadas;
    }

    public void setListaImagenesEtiquetadas(Set<Imagen> listaImagenesEtiquetadas) {
        this.listaImagenesEtiquetadas = listaImagenesEtiquetadas;
    }
}
