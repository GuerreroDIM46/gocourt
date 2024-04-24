package es.mde.rest;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import es.mde.entidades.Jugador;
import es.mde.repositorios.JugadorDAO;

@RepositoryRestController
@Configuration
public class JugadorController {
    
    private JugadorDAO jugadorDAO;

    public JugadorController(JugadorDAO jugadorDAO) {
        this.jugadorDAO = jugadorDAO;
    }
    
    @GetMapping("/jugadores/search/jugadoresSinPaginacion")
    @ResponseBody
    public CollectionModel<PersistentEntityResource> getJugadoresSinPaginacion(PersistentEntityResourceAssembler assembler) {
        List<Jugador> jugadores = jugadorDAO.getJugadoresSinPaginacion();
        return assembler.toCollectionModel(jugadores);
    }
    
    @GetMapping("/jugadores/search/jugadoresNivelSimilar")
    @ResponseBody
    public CollectionModel<PersistentEntityResource> getJugadoresNivelSimilar(@RequestParam Long id, PersistentEntityResourceAssembler assembler) {
        List<Jugador> jugadores = jugadorDAO.getJugadoresNivelSimilar(id);
        return assembler.toCollectionModel(jugadores);
    }
    
    @GetMapping("/jugadores/search/federadosNivelSimilar")
    @ResponseBody
    public CollectionModel<PersistentEntityResource> getFederadosNivelSimilar(@RequestParam Long id, PersistentEntityResourceAssembler assembler) {
        List<Jugador> jugadores = jugadorDAO.getFederadosNivelSimilar(id);
        return assembler.toCollectionModel(jugadores);
    }

}
