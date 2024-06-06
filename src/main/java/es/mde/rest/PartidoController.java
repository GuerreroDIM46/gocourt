package es.mde.rest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import es.mde.entidades.Partido;
import es.mde.repositorios.PartidoDAO;
import es.mde.services.PartidoService;

@RepositoryRestController
@Configuration
public class PartidoController {

    private PartidoDAO partidoDAO;

    public PartidoController(PartidoDAO partidoDAO) {
        this.partidoDAO = partidoDAO;
    }
    
    @Autowired
    private PartidoService partidoService;

    @GetMapping("/partidos/search/partidosHistoricos")
    @ResponseBody
    public CollectionModel<PersistentEntityResource> getPartidosHistoricos(
            PersistentEntityResourceAssembler assembler) {
        List<Partido> partidos = partidoDAO.getPartidosHistoricos();
        return assembler.toCollectionModel(partidos);
    }

    @GetMapping("/partidos/search/partidosValidados")
    @ResponseBody
    public CollectionModel<PersistentEntityResource> getPartidosValidados(
            PersistentEntityResourceAssembler assembler) {
        List<Partido> partidos = partidoDAO.getPartidosValidados();
        return assembler.toCollectionModel(partidos);
    }

    @GetMapping("/partidos/search/partidosPorConfirmar")
    @ResponseBody
    public CollectionModel<PersistentEntityResource> getPartidosPorConfirmar(
            PersistentEntityResourceAssembler assembler) {
        List<Partido> partidos = partidoDAO.getPartidosPorConfirmar();
        return assembler.toCollectionModel(partidos);
    }
    
    @GetMapping("/partidos/search/partidosCoincidentes")
    public ResponseEntity<List<Partido>> getCoincidentes(@RequestBody Partido partido) {
        List<Partido> partidos = new ArrayList<Partido>();
        partidos = partidoDAO.getPartidosByCampoYFechaHora(partido.getCampo().getId(), partido.getCuando());
        return new ResponseEntity<List<Partido>>(partidos, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Partido> crearPartido(@RequestBody Partido partido) {
        Partido nuevoPartido = partidoService.crearPartido(partido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPartido);
    }
    
}
