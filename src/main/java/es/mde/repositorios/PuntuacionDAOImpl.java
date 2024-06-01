package es.mde.repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.mde.entidades.Puntuacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
public class PuntuacionDAOImpl implements PuntuacionDAOCustom {
	
    @Autowired
    PuntuacionDAO puntuacionDAO;
    @PersistenceContext
    EntityManager entityManager;
    
    public String actualizarAsistencia(Long id, boolean aceptado) {
    	
    	Puntuacion puntuacion = puntuacionDAO.findById(id).orElse(null);
    	
        if (puntuacion == null) {
            return "Error: No se encontró la puntuación.";
        }
        
    	puntuacion.setAceptado(aceptado); 
    	puntuacionDAO.save(puntuacion);
    	return aceptado ? "Asistencia confirmada." : "Invitación rechazada.";
    }

}
