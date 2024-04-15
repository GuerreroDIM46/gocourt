package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.Federado;

@RepositoryRestResource(path = "federados", itemResourceRel = "federado", collectionResourceRel = "federados")
public interface FederadoDAO extends JpaRepository<Federado, Long> {
  
}
