package es.mde.repositorios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import es.mde.entidades.Federado;
import es.mde.entidades.Jugador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class FederadoDAOImpl implements FederadoDAOCustom {
    
    @Autowired
    FederadoDAO federadoDAO;
    @Autowired
    JugadorDAO jugadorDAO;
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public List<Federado> getFederadosSinPaginacion() {
        return federadoDAO.findAll();
    }
    
    @Override
    public List<Federado> encontrarFederadosParaCompetir(Long jugadorId, float puntuacionMinima,
            float rangoInferior, float rangoSuperior) {   
        List<Federado> respuesta = new ArrayList<Federado>();
        Jugador jugador = jugadorDAO.findById(jugadorId).orElse(null);
        if (jugador != null) {
            Float valorSuperior = jugador.getHandicap() + rangoSuperior;
            Float valorInferior = jugador.getHandicap() - rangoInferior;
            respuesta = federadoDAO.findAll()
                    .stream()
                    .sorted(Comparator.comparingDouble(Jugador::getHandicap))
                    .filter(j -> !j.isProfesional() && j.getCampo().equals(jugador.getCampo()))
                    .filter(j -> j.getHandicap() >= valorInferior && j.getHandicap() <= valorSuperior)                    
                    .collect(Collectors.toList());
            }
            return respuesta;
        }

           
}
