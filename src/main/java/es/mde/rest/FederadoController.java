package es.mde.rest;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import es.mde.entidades.Federado;
import es.mde.repositorios.FederadoDAO;


@RepositoryRestController
@Configuration
public class FederadoController {
    
    private FederadoDAO federadoDAO;

    public FederadoController(FederadoDAO federadoDAO) {
        this.federadoDAO = federadoDAO;
    }
    
    @GetMapping("/federados/search/federadosSinPaginacion")
    @ResponseBody
    public CollectionModel<PersistentEntityResource> getFederadosSinPaginacion(PersistentEntityResourceAssembler assembler) {
        System.err.println("prueba");
        List<Federado> federados = federadoDAO.getFederadosSinPaginacion();

        return assembler.toCollectionModel(federados);
    }
    
    @GetMapping("/federados/search/jugadoresNivelSimilar")
    @ResponseBody
    public CollectionModel<PersistentEntityResource> getFederadosNivelSimilar(@RequestParam Long id, PersistentEntityResourceAssembler assembler) {
        List<Federado> federados = federadoDAO.getFederadosNivelSimilar(id);
        return assembler.toCollectionModel(federados);
    }

}
