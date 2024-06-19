package es.mde.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PRINCIPIANTE")
public class Principiante extends Jugador {

    private float puntuacionLargo, puntuacionCorto;
    
    public Principiante() {}
      
        public float getPuntuacionLargo() {
        return puntuacionLargo;
    }

    public void setPuntuacionLargo(float puntuacionLargo) {
        if (puntuacionLargo < 1 || puntuacionLargo > 100) {
            throw new PuntuacionFueraDeRangoException("La puntuación largo debe estar entre 1 y 100.");
        }
        this.puntuacionLargo = puntuacionLargo;
    }

    public float getPuntuacionCorto() {
        return puntuacionCorto;
    }
    
    public void setPuntuacionCorto(float puntuacionCorto) {
        if (puntuacionLargo < 1 || puntuacionLargo > 100) {
            throw new PuntuacionFueraDeRangoException("La puntuación largo debe estar entre 1 y 100.");
        }
        this.puntuacionCorto = puntuacionCorto;
    }
    
    @Override
    public float getHandicap() {
        return (float) (49 + (72 * ((Math.log10(30.0 / puntuacionLargo) + Math.log10(70.0 / puntuacionCorto)))));
    }

    @Override
    public String getTipo() {
        return "principiante";
    }

    public class PuntuacionFueraDeRangoException extends RuntimeException {
        public PuntuacionFueraDeRangoException(String message) {
            super(message);
        }
    }
    
}
