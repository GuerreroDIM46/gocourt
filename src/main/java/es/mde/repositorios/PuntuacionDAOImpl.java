package es.mde.repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.mde.entidades.Puntuacion;
import es.mde.services.EmailInicializerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
public class PuntuacionDAOImpl implements PuntuacionDAOCustom {
	
    @Autowired
    PuntuacionDAO puntuacionDAO;
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    EmailInicializerService emailInicializerService;
    
    public String actualizarAsistencia(Long id, boolean aceptado) {
    	
    	Puntuacion puntuacion = puntuacionDAO.findById(id).orElse(null);
    	
        if (puntuacion == null) {
            return "Error: No se encontró la puntuación.";
        }
        
    	puntuacion.setAceptado(aceptado); 
    	puntuacionDAO.save(puntuacion);
    	
    	if (aceptado == false) {
    	    emailInicializerService.inicializarSugerencia(id);
    	}
    	    
    	return aceptado ? "Asistencia confirmada." : "Invitación rechazada.";
    }

}
