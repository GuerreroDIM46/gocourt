package es.mde.entidades;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FEDERADO")
public class Federado extends Jugador {
    
    private boolean profesional;
    private float handicap;    
    
    public Federado() {}

    public boolean isProfesional() {
        return profesional;
    }

    public void setProfesional(boolean profesional) {
        this.profesional = profesional;
    }

    public float getHandicap() {
        return handicap;
    }

    public void setHandicap(float handicap) {
        this.handicap = handicap;
    }
    

        
}
