package es.mde.repositorios;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.Jugador;

@RepositoryRestResource(path = "jugadores", itemResourceRel = "jugador", collectionResourceRel = "jugadores")
public interface JugadorDAO extends JpaRepository<Jugador, Long>, JugadorDAOCustom{
    List<Jugador> findByNombre(String txt);
    
}
