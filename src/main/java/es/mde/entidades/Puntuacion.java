package es.mde.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PUNTUACIONES")
public class Puntuacion {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)  
    private Long id;
    
    private float puntuacion;
	private String estado;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JUGADOR")
    private Jugador jugador;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARTIDO")
    private Partido partido;
    
    public Puntuacion() {}

	public Long getId() {
		return id;
	}
		
	public void setId(Long id) {
		this.id = id;
	}

	public float getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(float puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isAceptado() {
		return this.estado == "aceptado";
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Partido getPartido() {
		return partido;
	}

	public void setPartido(Partido partido) {
		this.partido = partido;
	}
	
	public String getNombreCompleto() {
	    return jugador.getNombre() + " " + jugador.getApellido1() + " " + jugador.getApellido2();
	}
	
	public String getTipo() {
	    return getJugador().getTipo();
	}

}
