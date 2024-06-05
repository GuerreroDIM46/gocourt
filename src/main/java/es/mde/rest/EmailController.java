package es.mde.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import es.mde.entidades.Partido;
import es.mde.entidades.Puntuacion;
import es.mde.repositorios.PartidoDAO;
import es.mde.repositorios.PuntuacionDAO;
import es.mde.services.EmailInicializerService;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    
    @Autowired
    PartidoDAO partidoDAO;

    @Autowired
    PuntuacionDAO puntuacionDAO;

    @Autowired
    private EmailInicializerService emailInicializerService;

    @PostMapping("/sendComunicadoAsignacionDePartido")
    public String sendComunicadoAsignacionDePartido(@RequestBody Map<String, Long> ids) {
        Long partidoId = ids.get("partidoId");
        Long puntuacion1Id = ids.get("puntuacion1Id");
        Long puntuacion2Id = ids.get("puntuacion2Id");

        return emailInicializerService.enviarComunicadoAsignacionServicio(partidoId, puntuacion1Id,
                puntuacion2Id);

    }
    
    @PostMapping("/sendPartidoAceptado")
    public String sendPartidoAceptado(@RequestBody Map<String, Long> ids) {
        Long partidoId = ids.get("partidoId");
        Long puntuacion1Id = ids.get("puntuacion1Id");
        Long puntuacion2Id = ids.get("puntuacion2Id");
        Partido partido = partidoDAO.findById(partidoId).orElse(null);
        Puntuacion puntuacion1 = puntuacionDAO.findById(puntuacion1Id).orElse(null);
        Puntuacion puntuacion2 = puntuacionDAO.findById(puntuacion2Id).orElse(null);

        return emailInicializerService.inicializarEmailEnvioPartidoAceptado(partido, puntuacion1,
                puntuacion2);

    }


}
