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
    public List<Federado> encontrarFederadosParaCompetir(Jugador jugador, float rangoInferior,
            float rangoSuperior) {
        List<Federado> respuesta = new ArrayList<Federado>();
        if (jugador == null) {
            throw new JugadorNoEncontradoException("Jugador no proporcionado o no encontrado");
        }
        Float handicap = jugador.getHandicap();
        Float valorSuperior = handicap + rangoSuperior;
        Float valorInferior = handicap - rangoInferior;

        respuesta = federadoDAO.findAll().stream()
                .sorted(Comparator.comparingDouble(Jugador::getHandicap))
                .filter(j -> !j.isProfesional() && j.getCampo().equals(jugador.getCampo()))
                .filter(j -> j.getHandicap() >= valorInferior && j.getHandicap() <= valorSuperior)
                .collect(Collectors.toList());

        return respuesta;
    }

    public class JugadorNoEncontradoException extends RuntimeException {
        public JugadorNoEncontradoException(String message) {
            super(message);
        }
    }
}
