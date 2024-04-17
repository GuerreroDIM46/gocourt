package es.mde.repositorios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import es.mde.entidades.Jugador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
public class JugadorDAOImpl implements JugadorDAOCustom{
    
    @Autowired
    JugadorDAO jugadorDAO;
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Jugador> getJugadoresSinPaginacion() {
        return jugadorDAO.findAll();
    }

}
