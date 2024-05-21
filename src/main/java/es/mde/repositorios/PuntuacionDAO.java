package es.mde.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.mde.entidades.Puntuacion;

@RepositoryRestResource(path = "puntuaciones", itemResourceRel = "puntuacion", collectionResourceRel = "puntuaciones")
public interface PuntuacionDAO extends JpaRepository<Puntuacion, Long> {

}
