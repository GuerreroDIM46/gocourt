package es.mde.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.mde.repositorios.PuntuacionDAO;

@RepositoryRestController
@Configuration
public class PuntuacionController {

	private PuntuacionDAO puntuacionDAO;

	public PuntuacionController(PuntuacionDAO puntuacionDAO) {
		this.puntuacionDAO = puntuacionDAO;
	}

	@PatchMapping("/puntuaciones/search/actualizarAsistencia")
	@ResponseBody
	public String patchActualizarAsistencia(@RequestParam Long id, @RequestParam boolean aceptado) {
		return puntuacionDAO.actualizarAsistencia(id, aceptado);
	}

}
