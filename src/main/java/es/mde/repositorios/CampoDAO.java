package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.Campo;

@RepositoryRestResource(path = "campos", itemResourceRel = "campo", collectionResourceRel = "campos")
public interface CampoDAO extends JpaRepository<Campo, Long>{

}
