package es.mde.entidades;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import es.mde.repositorios.PartidoListener;
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
import jakarta.persistence.Table;

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
    private Campo campo;
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
		this.cuando = cuando;
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

}
