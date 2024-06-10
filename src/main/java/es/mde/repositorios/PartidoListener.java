package es.mde.repositorios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.mde.entidades.Partido;
import es.mde.services.ValidationService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;

@Component
public class PartidoListener {
   
//    @Autowired
    private PartidoDAO partidoDAO;

    @Autowired
    public void init(PartidoDAO partidoDAO) {
        System.err.println("sgfsdgfkjwsdfgf");
        this.partidoDAO = partidoDAO;
    }



    @PrePersist
	public void validacionPartido(Partido partido) {
        System.err.println("Prueba");
		try {
		    List<Partido> partidosCoincidentes = partidoDAO.getPartidosByCampoYFechaHora(partido.getCampo().getId(), partido.getCuando());
		    boolean valido = partidosCoincidentes.isEmpty();
		    System.err.println("el partido es valido: " + valido);
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
