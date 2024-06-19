package es.mde.entidades;


import java.util.ArrayList;
import java.util.Collection;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "JUGADORES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
@DiscriminatorValue("JUGADOR")
public abstract class Jugador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)  
    private Long id;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El primer apellido no puede estar vacío")
    private String apellido1;

    @NotBlank(message = "El segundo apellido no puede estar vacío")
    private String apellido2;

    @NotBlank(message = "El DNI no puede estar vacío")
    private String dni;

    @NotBlank(message = "El teléfono no puede estar vacío")
    private String telefono;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    private String email;
    
    @NotNull(message = "El campo no puede ser nulo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAMPO")
    private Campo campo;
    
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Puntuacion.class, mappedBy = "jugador")
    private Collection<Puntuacion> puntuaciones = new ArrayList<>();
           
    public Jugador() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public abstract float getHandicap();
    
    public abstract String getTipo();

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Campo getCampo() {
        return campo;
    }

    public void setCampo(Campo campo) {
        this.campo = campo;
    }
                   
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Puntuacion> getPuntuaciones() {
		return puntuaciones;
	}

	public void setPuntuaciones(Collection<Puntuacion> puntuaciones) {
		this.puntuaciones = puntuaciones;
	}

	public String getNombreCampo() {
        return this.getCampo().getNombre();
    }
    
    public void addPuntuacion(Puntuacion puntuacion) {
        getPuntuaciones().add(puntuacion);
        puntuacion.setJugador(this);
    }
       
}
