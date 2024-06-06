package es.mde.repositorios;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.mde.entidades.Partido;

@RepositoryRestResource(path = "partidos", itemResourceRel = "partido", collectionResourceRel = "partidos")
public interface PartidoDAO extends JpaRepository<Partido, Long>, PartidoDAOCustom {
	List<Partido> findByCuando(LocalDateTime cuando);
}
