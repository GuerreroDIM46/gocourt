package es.mde.repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import es.mde.entidades.Partido;
import es.mde.services.PartidoService;
import jakarta.persistence.PrePersist;

@Component
public class PartidoListener {

    PartidoDAO partidoDAO;

    @Autowired
    public void init(PartidoDAO partidoDAO) {
        this.partidoDAO = partidoDAO;
    }
    
    @Autowired
    PartidoService partidoService;
    
    @PrePersist
    public void validacionAntesDeGuardar(Partido partido) {        
        if (!partidoService.isPartidoValido(partido)) {
            throw new PartidoMismoHorarioException("Partidos concurrentes en el tiempo y en el espacio fail");
        }
    }
    
    public class PartidoMismoHorarioException extends RuntimeException {
        public PartidoMismoHorarioException(String message) {
            super(message);
        }
    }
    
}
