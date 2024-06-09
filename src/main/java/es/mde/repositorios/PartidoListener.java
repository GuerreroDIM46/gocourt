package es.mde.repositorios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
	public void validacionPartido(Partido partido) {
		System.err.println("punto de fallo: 1");
		Long partidoCampoId = partido.getCampo().getId();
		LocalDateTime partidoCuando = partido.getCuando();
		System.err.println("datos de partido: " + partidoCampoId + ", " + partidoCuando );
		System.err.println("punto de fallo: 2");
		try {
			List<Partido> partidosCoincidentes = partidoDAO.findAll()
	                .stream()
	                .filter(p -> p.getCuando().equals(partidoCuando))
	                .filter(p -> p.getCampo().getId().equals(partidoCampoId))
	                .collect(Collectors.toList());
			System.err.println("punto de fallo: 3");
			boolean valido = partidosCoincidentes.isEmpty();
			System.err.println("punto de fallo: 4");
			System.err.println("partido es valido: " + valido);
			System.err.println("punto de fallo: 5");
		} catch (Exception e) {
			System.err.println(e);
		}

	}

    
    public class PartidoMismoHorarioException extends RuntimeException {
        public PartidoMismoHorarioException(String message) {
            super(message);
        }
    }
    
}
