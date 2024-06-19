package es.mde.entidades;


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
    
    @Override
    public float getHandicap() {
        return handicap;
    }

    public void setHandicap(float handicap) {
        if (handicap < -10 || handicap > 34) {
            throw new HandicapFueraDeRangoException("El rango del Handicap debe estar entre -10 y 34.");
        }
        this.handicap = handicap;
    }

    @Override
    public String getTipo() {
        return "federado";
    }  
    
    public class HandicapFueraDeRangoException extends RuntimeException {
        public HandicapFueraDeRangoException(String message) {
            super(message);
        }
    }
        
}
