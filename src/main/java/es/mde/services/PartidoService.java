package es.mde.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.mde.entidades.Partido;
import es.mde.repositorios.PartidoDAO;

@Service
public class PartidoService {
    
    @Autowired
    private PartidoDAO partidoDAO;
    
    private boolean isPartidoValido(Partido partido) {
        List<Partido> partidosCoincidentes = partidoDAO.getPartidosByCampoYFechaHora(partido.getCampo().getId(), partido.getCuando());
        return partidosCoincidentes.isEmpty();
    }
    
    public Partido crearPartido(Partido partido) {
        if (isPartidoValido(partido)) {
            return partidoDAO.save(partido);
        } else {
            throw new PartidoMismoHorarioException("Partidos concurrentes en el tiempo y en el espacio fallan");
        }
    }
    
    public class PartidoMismoHorarioException extends RuntimeException {
        public PartidoMismoHorarioException(String message) {
            super(message);
        }
    }
}
