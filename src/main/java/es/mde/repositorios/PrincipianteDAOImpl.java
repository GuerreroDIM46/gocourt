package es.mde.repositorios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import es.mde.entidades.Principiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
public class PrincipianteDAOImpl implements PrincipianteDAOCustom {
    
    @Autowired
    PrincipianteDAO principianteDAO;
    @PersistenceContext
    EntityManager entityManager;
    
    @Override
    public List<Principiante> getPrincipiantesSinPaginacion() {
        return principianteDAO.findAll();
    }
    
    
}
