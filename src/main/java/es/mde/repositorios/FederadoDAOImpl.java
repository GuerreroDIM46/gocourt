package es.mde.repositorios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import es.mde.entidades.Federado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class FederadoDAOImpl implements FederadoDAOCustom {
    
    @Autowired
    FederadoDAO federadoDAO;
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public List<Federado> getFederadosSinPaginacion() {
        return federadoDAO.findAll();
    }
    
    @Override
    public List<Federado> getFederadosNivelSimilar(Long id) {
        List<Federado> federadosPorHandicap = federadoDAO.findAll()
                .stream()
                .sorted(Comparator.comparingDouble(Federado::getHandicap))
                .collect(Collectors.toList());

        List<Federado> federadosNivelSimilar = new ArrayList<>();
        int indice = IntStream.range(0, federadosPorHandicap.size())
                .filter(i -> federadosPorHandicap.get(i).getId().equals(id))
                .findFirst().orElse(-1);
        if (indice != -1) {
            if (indice + 1 < federadosPorHandicap.size()) {
                federadosNivelSimilar.add(federadosPorHandicap.get(indice + 1));
            }
            if (indice + 2 < federadosPorHandicap.size()) {
                federadosNivelSimilar.add(federadosPorHandicap.get(indice + 2));
            }

            if (indice > 0) {
                federadosNivelSimilar.add(0, federadosPorHandicap.get(indice - 1));  
            }
            if (indice > 1) {
                federadosNivelSimilar.add(0, federadosPorHandicap.get(indice - 2));  
            }
        }
        return federadosNivelSimilar;
    }
    
}
