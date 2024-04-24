package es.mde.repositorios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import es.mde.entidades.Federado;
import es.mde.entidades.Jugador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
public class JugadorDAOImpl implements JugadorDAOCustom{
    
    @Autowired
    JugadorDAO jugadorDAO;
    @Autowired
    FederadoDAO federadoDAO;
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Jugador> getJugadoresSinPaginacion() {
        return jugadorDAO.findAll();
    }

    @Override
    public List<Jugador> getJugadoresNivelSimilar(Long id) {
        List<Jugador> jugadoresPorHandicap = jugadorDAO.findAll()
                .stream()
                .sorted(Comparator.comparingDouble(Jugador::getHandicap))
                .collect(Collectors.toList());

        List<Jugador> jugadoresNivelSimilar = new ArrayList<>();
        int indice = IntStream.range(0, jugadoresPorHandicap.size())
                .filter(i -> jugadoresPorHandicap.get(i).getId().equals(id))
                .findFirst().orElse(-1);
        if (indice != -1) {
            if (indice + 1 < jugadoresPorHandicap.size()) {
                jugadoresNivelSimilar.add(jugadoresPorHandicap.get(indice + 1));
            }
            if (indice + 2 < jugadoresPorHandicap.size()) {
                jugadoresNivelSimilar.add(jugadoresPorHandicap.get(indice + 2));
            }

            if (indice > 0) {
                jugadoresNivelSimilar.add(0, jugadoresPorHandicap.get(indice - 1));  
            }
            if (indice > 1) {
                jugadoresNivelSimilar.add(0, jugadoresPorHandicap.get(indice - 2));  
            }
        }
        return jugadoresNivelSimilar;
    }
    
    @Override
    public List<Jugador> getFederadosNivelSimilar(Long id) {
        List<Jugador> jugadoresPorHandicap = jugadorDAO.findAll()
                .stream()
                .sorted(Comparator.comparingDouble(Jugador::getHandicap))
                .collect(Collectors.toList());

        List<Federado> federados = federadoDAO.findAll();
        List<Long> idsFederados = federados.stream()
                                           .map(Federado::getId)
                                           .collect(Collectors.toList());

        List<Jugador> jugadoresFederadosNivelSimilar = new ArrayList<>();
        int indice = IntStream.range(0, jugadoresPorHandicap.size())
                              .filter(i -> jugadoresPorHandicap.get(i)
                                                               .getId()
                                                               .equals(id))
                              .findFirst()
                              .orElse(-1);

        if (indice != -1) {
            int contador = 0;
            int i = indice + 1;
            while (i < jugadoresPorHandicap.size() && contador < 2) {
                if (idsFederados.contains(jugadoresPorHandicap.get(i).getId())) {
                    jugadoresFederadosNivelSimilar.add(jugadoresPorHandicap.get(i));
                    contador++;
                }
                i++;
            }
            contador = 0;
            i = indice - 1;
            while (i >= 0 && contador < 2) {
                if (idsFederados.contains(jugadoresPorHandicap.get(i).getId())) {
                    jugadoresFederadosNivelSimilar.add(0, jugadoresPorHandicap.get(i)); 
                    contador++;
                }
                i--;
            }
        }

        return jugadoresFederadosNivelSimilar;
    }
   
}
