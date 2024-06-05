package es.mde.repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import es.mde.entidades.Partido;
import es.mde.entidades.Puntuacion;
import es.mde.services.EmailInicializerService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
public class PuntuacionDAOImpl implements PuntuacionDAOCustom {
    
    @Autowired
    PartidoDAO partidoDAO;
    
    @Autowired
    PuntuacionDAO puntuacionDAO;
    
    @PersistenceContext
    EntityManager entityManager;
    
    @Autowired
    EmailInicializerService emailInicializerService;

    public String actualizarAsistencia(Long id, boolean aceptado) {

        Puntuacion puntuacion1 = puntuacionDAO.findById(id).orElse(null);

        if (puntuacion1 == null) {
            return "Error: No se encontró la puntuación.";
        }

        puntuacion1.setAceptado(aceptado);
        puntuacionDAO.save(puntuacion1);

        if (aceptado == false) {
            emailInicializerService.inicializarSugerencia(id);
        }
        
        if (aceptado == true) {
            Partido partido = puntuacion1.getPartido();
            Puntuacion puntuacion2 = partido.getPuntuaciones()
                                            .stream()
                                            .filter(p -> !p.equals(puntuacion1))
                                            .findFirst()
                                            .orElse(null);
            if (puntuacion2.isAceptado()) {
                emailInicializerService.inicializarEmailEnvioPartidoAceptado(partido, puntuacion1, puntuacion2);
            }
        }
        

        return aceptado ? "Asistencia confirmada." : "Invitación rechazada.";
    }

    public String actualizarCompartidoTelefono(Long id, boolean compartidoTelefono) {

        Puntuacion puntuacion1 = puntuacionDAO.findById(id).orElse(null);

        if (puntuacion1 == null) {
            return "Error: No se encontró la puntuación.";
        }

        puntuacion1.setCompartidoTelefono(compartidoTelefono);
        puntuacionDAO.save(puntuacion1);
        
        if (compartidoTelefono == true) {
            Partido partido = puntuacion1.getPartido();
            Puntuacion puntuacion2 = partido.getPuntuaciones()
                                            .stream()
                                            .filter(p -> !p.equals(puntuacion1))
                                            .findFirst()
                                            .orElse(null);
            if (puntuacion2.isCompartidoTelefono()) {
                emailInicializerService.inicializarEmailCompartirTelefono(partido, puntuacion1, puntuacion2);
            }
        }
        // if (compartidoTelefono == false) {
        // emailInicializerService.inicializarSugerencia(id);
        // }

        return compartidoTelefono ? "Aceptó compartir el numero de telefono. Si el otro jugador tambien acepta,"
                + "recibira un email con sus datos de contacto"
                : "Comparticion de telefono rechazada.";
    }


}
