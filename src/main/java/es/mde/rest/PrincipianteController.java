package es.mde.rest;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import es.mde.entidades.Jugador;
import es.mde.entidades.Principiante;
import es.mde.repositorios.PrincipianteDAO;

@RepositoryRestController
@Configuration
public class PrincipianteController {
    
    private PrincipianteDAO principianteDAO;
    
    public PrincipianteController(PrincipianteDAO principianteDAO) {
        this.principianteDAO = principianteDAO;
    }
    
    @GetMapping("/principiantes/search/principiantesSinPaginacion")
    @ResponseBody
    public CollectionModel<PersistentEntityResource> getPrincipiantesSinPaginacion(PersistentEntityResourceAssembler assembler) {
        System.err.println("prueba");
        List<Principiante> principiantes = principianteDAO.getPrincipiantesSinPaginacion();

        return assembler.toCollectionModel(principiantes);
    }
    
}
