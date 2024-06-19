package es.mde.entidades;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import es.mde.repositorios.PartidoListener;
import es.mde.services.TokenGenerator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "PARTIDOS")
@EntityListeners(PartidoListener.class)
public class Partido {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)  
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAMPO")
    @NotNull(message = "El campo no puede ser nulo.")
    private Campo campo;
    
    @JsonIgnore
    private String token;
    
    @NotNull(message = "La fecha y hora del partido no pueden ser nulas.")
    private LocalDateTime cuando;
    
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Puntuacion.class, mappedBy = "partido")
    private Collection<Puntuacion> puntuaciones = new ArrayList<>();
    
    public Partido() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
		
	public Campo getCampo() {
		return campo;
	}
	
    public String getNombreCampo() {
        return campo.getNombre();
    }	

	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	public LocalDateTime getCuando() {
		return cuando;
	}

	public void setCuando(LocalDateTime cuando) {
	    if (cuando.isBefore(LocalDateTime.now())) {
	        throw new AntesDeHoyExcepcion("La fecha del partido no puede ser anterior a la fecha actual.");
	    }
	    if (cuando.toLocalTime().isBefore(LocalTime.of(8, 0)) || cuando.toLocalTime().isAfter(LocalTime.of(20, 0))) {
	        throw new FueraRangoHorarioException("La hora del partido debe estar entre las 8:00 y las 20:00.");
	    }
	    int minutos = cuando.getMinute();
	    int ajusteMinutos = (minutos % 20) < 10 ? -(minutos % 20) : (20 - minutos % 20);
	    this.cuando = cuando.plusMinutes(ajusteMinutos);
	} 

	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Collection<Puntuacion> getPuntuaciones() {
		return puntuaciones;
	}

	public void setPuntuaciones(Collection<Puntuacion> puntuaciones) {
		this.puntuaciones = puntuaciones;
	}
    
    public void addPuntuacion(Puntuacion puntuacion) {
        getPuntuaciones().add(puntuacion);
        puntuacion.setPartido(this);
    }
    
    @PrePersist
    @PreUpdate
    private void AsignarToken() {
        if (this.token == null) {
            this.token = TokenGenerator.generarToken();
        }
    }
    
    public class AntesDeHoyExcepcion extends RuntimeException {
        public AntesDeHoyExcepcion(String message) {
            super(message);
        }
    }
    
    public class FueraRangoHorarioException extends RuntimeException {
        public FueraRangoHorarioException(String message) {
            super(message);
        }
    }

}
