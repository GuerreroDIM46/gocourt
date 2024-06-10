package es.mde.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import es.mde.entidades.Partido;
import es.mde.repositorios.PartidoDAO;


@Service
public class ValidationService {
   
    @Autowired
    PartidoDAO partidoDAO;
   
    public boolean isValidoByFechaHoraCampo(Partido partido) {
        List<Partido> partidosCoincidentes = partidoDAO.getPartidosByCampoYFechaHora(partido.getCampo().getId(), partido.getCuando());
        return partidosCoincidentes.isEmpty();
    }
    
    
}
