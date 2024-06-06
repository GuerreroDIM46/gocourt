package es.mde.repositorios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import es.mde.entidades.Partido;
import jakarta.persistence.PrePersist;

@Component
public class PartidoListener {

    @Autowired
    private PartidoDAO partidoDAO;
    
    private boolean isPartidoValido(Partido partido) {
        List<Partido> partidosCoincidentes = partidoDAO.getPartidosByCampoYFechaHora(partido.getCampo().getId(), partido.getCuando());
        return partidosCoincidentes.isEmpty();
    }

    @PrePersist
    public void validacionAntesDeGuardar(Partido partido) { 
        System.err.println("Es valido el partido? " + isPartidoValido(partido));
        if (!isPartidoValido(partido)) {
            
            throw new PartidoMismoHorarioException("Partidos concurrentes en el tiempo y en el espacio fallan");
        }
    }
    
    public class PartidoMismoHorarioException extends RuntimeException {
        public PartidoMismoHorarioException(String message) {
            super(message);
        }
    }
    
}
