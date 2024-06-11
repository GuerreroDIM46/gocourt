package es.mde.repositorios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import es.mde.entidades.Partido;
import es.mde.entidades.Puntuacion;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Component
public class PuntuacionListener {

    private static PartidoDAO partidoDAO;

    @Autowired
    public void init(PartidoDAO partidoDAO) {
        PuntuacionListener.partidoDAO = partidoDAO;
    }
    
//    @PreUpdate No funciona (todavia)
    @PrePersist
    public void validacionPuntuacion(Puntuacion puntuacion) {
        List<Partido> partidosCoincidentes = partidoDAO.getPartidosConfirmadosByJugadorYFecha(
                puntuacion.getJugador().getId(), puntuacion.getPartido().getCuando().toLocalDate());
        if (!partidosCoincidentes.isEmpty()) {
            System.err.println("Error: hay partidos coincidentes");
            throw new JugadorConPartidosElMismoDiaException(
                    "El jugador tiene partido el mismo dia");
        } else {
            System.err.println("Partido creado sin pegas");
        }
    }

    public class JugadorConPartidosElMismoDiaException extends RuntimeException {
        public JugadorConPartidosElMismoDiaException(String message) {
            super(message);
        }
    }
}
