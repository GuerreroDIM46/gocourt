package es.mde.repositorios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import es.mde.entidades.Partido;
import jakarta.persistence.PrePersist;

@Component
public class PartidoListener {
   
    private static PartidoDAO partidoDAO;

    @Autowired
    public void init(PartidoDAO partidoDAO) {
        PartidoListener.partidoDAO = partidoDAO;
    }

//  @PreUpdate No funciona (todavia)
    @PrePersist
	public void validacionPartido(Partido partido) {
		    List<Partido> partidosCoincidentes = partidoDAO.getPartidosConfirmadosByCampoYFechaHora(partido.getCampo().getId(), partido.getCuando());
		    if (!partidosCoincidentes.isEmpty()) {
		        System.err.println("Error: hay partidos coincidentes");
                throw new PartidoMismoHorarioException("Partidos concurrentes en el tiempo y en el espacio fallan");
            } else {
                System.err.println("Partido creado sin pegas");
            }
	}
    
    public class PartidoMismoHorarioException extends RuntimeException {
        public PartidoMismoHorarioException(String message) {
            super(message);
        }
    }    
}
