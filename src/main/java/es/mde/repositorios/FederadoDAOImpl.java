package es.mde.repositorios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import es.mde.entidades.Federado;
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
           
}
