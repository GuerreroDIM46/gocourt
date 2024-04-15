package es.mde.entidades;

import java.util.ArrayList;
import java.util.Collection;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "CAMPOS")
public class Campo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)  
    private Long id;
    
    private String nombre, provincia;
    private float valorCampo, valorSlope;
    
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Jugador.class, mappedBy = "campo")
    private Collection<Jugador> jugadores = new ArrayList<>();
   
    public Campo() {}
    
    public Campo(String nombre, String provincia, float valorCampo, float valorSlope) {
        super();
        this.nombre = nombre;
        this.provincia = provincia;
        this.valorCampo = valorCampo;
        this.valorSlope = valorSlope;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public float getValorCampo() {
        return valorCampo;
    }

    public void setValorCampo(float valorCampo) {
        this.valorCampo = valorCampo;
    }

    public float getValorSlope() {
        return valorSlope;
    }

    public void setValorSlope(float valorSlope) {
        this.valorSlope = valorSlope;
    }

    public Collection<Jugador> getJugadores() {
        return jugadores;
    }
   
    public void addJugador(Jugador jugador) {
        getJugadores().add(jugador);
        jugador.setCampo(this);
    }
    
}
