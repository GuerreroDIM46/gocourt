package es.mde.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.mde.entidades.Principiante;

@RepositoryRestResource(path = "principiantes", itemResourceRel = "principiante", collectionResourceRel = "principiantes")
public interface PrincipianteDAO  extends JpaRepository<Principiante, Long>, PrincipianteDAOCustom {
    List<Principiante> findByNombre(String txt);
}
