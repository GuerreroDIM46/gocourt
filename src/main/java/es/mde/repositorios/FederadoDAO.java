package es.mde.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.Federado;

@RepositoryRestResource(path = "federados", itemResourceRel = "federado", collectionResourceRel = "federados")
public interface FederadoDAO extends JpaRepository<Federado, Long>, FederadoDAOCustom {
    List<Federado> findByNombre(String txt);
  
}
