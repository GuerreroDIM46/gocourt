package es.mde.rest;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import es.mde.entidades.Partido;
import es.mde.repositorios.PartidoDAO;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@RepositoryRestController
@Configuration
public class PartidoController {

    private PartidoDAO partidoDAO;

    public PartidoController(PartidoDAO partidoDAO) {
        this.partidoDAO = partidoDAO;
    }
    
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
    
    @GetMapping("/partidos/search/findByToken")
    @ResponseBody
    public ResponseEntity<PersistentEntityResource> getPartidoByToken(
            @RequestParam("token") String token,
            PersistentEntityResourceAssembler assembler) {
        Partido partido = partidoDAO.findByToken(token);
        if (partido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(partido));
    }
   
}
