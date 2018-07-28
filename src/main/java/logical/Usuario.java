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
    private String password;

    @NotNull
    private String nombres;

    private String apellidos;

    @NotNull
    private String sexo;

    @NotNull
    private Date fechaNacimiento;

    @NotNull
    @ManyToOne
    private Pais pais;

    private String ciudad;

    private String lugarDeEstudio;

    private String empleo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "autor")
    private Set<Post> posts;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "autor")
    private Set<ComentarioPost> comentarios;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "autor")
    private Set<ComentarioFoto> comentariosAlbum;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "usuario")
    private Set<Album> albumes;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario")
    private Set<Actividad> timeline;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "AMISTADES", joinColumns = { @JoinColumn(name = "USUARIO") }, inverseJoinColumns = {
            @JoinColumn (name = "AMIGO") })
    private Set<Usuario> amigos;

    @OneToOne(cascade = CascadeType.ALL)
    private Imagen fotoPerfil;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "emisor")
    private Set<SolicitudAmistad> solicitudesEnviadas;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "receptor")
    private Set<SolicitudAmistad> solicitudesRecibidas;

    @NotNull
    private boolean admin;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USUARIOS_TAGGED", joinColumns = { @JoinColumn(name = "idUsuario") }, inverseJoinColumns = {
            @JoinColumn (name = "IdImagen") })
    private Set<Imagen> listaImagenesEtiquetadas;

    @OneToMany(mappedBy = "usuario",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<LikePost> likeArticulo = new HashSet<>();

    @OneToMany(mappedBy = "usuario",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<LikePost> likePosts = new HashSet<>();

    public Usuario() {
    }

    public Usuario(String nombres, String apellidos, String correo, String password, String sexo, Date
            fechaNacimiento, Pais pais, String ciudad, String lugarDeEstudio, String empleo, Imagen fotoPerfil,
                   boolean admin) {
        this.correo = correo;
        this.password = password;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
        this.ciudad = ciudad;
        this.lugarDeEstudio = lugarDeEstudio;
        this.empleo = empleo;
        this.fotoPerfil = fotoPerfil;
        this.admin = admin;
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

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
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

    public Set<ComentarioPost> getCometarios() {
        return comentarios;
    }

    public void setCometarios(Set<ComentarioPost> comentarios) {
        this.comentarios = comentarios;
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

    public Set<SolicitudAmistad> getSolicitudesRecibidas() {
        return solicitudesRecibidas;
    }

    public void setSolicitudesRecibidas(Set<SolicitudAmistad> solicitudesRecibidas) {
        this.solicitudesRecibidas = solicitudesRecibidas;
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

    public Set<ComentarioPost> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<ComentarioPost> comentarios) {
        this.comentarios = comentarios;
    }

    public Set<ComentarioFoto> getComentariosAlbum() {
        return comentariosAlbum;
    }

    public void setComentariosAlbum(Set<ComentarioFoto> comentariosAlbum) {
        this.comentariosAlbum = comentariosAlbum;
    }

    public Set<LikePost> getLikeArticulo() {
        return likeArticulo;
    }

    public void setLikeArticulo(Set<LikePost> likeArticulo) {
        this.likeArticulo = likeArticulo;
    }

    public Set<LikePost> getLikePosts() {
        return likePosts;
    }

    public void setLikePosts(Set<LikePost> likePosts) {
        this.likePosts = likePosts;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
