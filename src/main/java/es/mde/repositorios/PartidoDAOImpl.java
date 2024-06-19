package es.mde.repositorios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters.DateToLocalDateConverter;
import org.springframework.transaction.annotation.Transactional;
import es.mde.entidades.Campo;
import es.mde.entidades.Jugador;
import es.mde.entidades.Partido;
import es.mde.entidades.Puntuacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Transactional
public class PartidoDAOImpl implements PartidoDAOCustom {

    @Autowired
    JugadorDAO jugadorDAO;
    @Autowired
    PartidoDAO partidoDAO;
    @PersistenceContext
    EntityManager entityManager;

    private boolean esAntesDeAhora(Partido partido) {
        return partido.getCuando().isBefore(LocalDateTime.now());
    }

    private boolean esDespuesDeAhora(Partido partido) {
        return partido.getCuando().isAfter(LocalDateTime.now());
    }

    private boolean todasLasPuntuacionesAceptadas(Partido partido) {
        return partido.getPuntuaciones().stream().allMatch(Puntuacion::isAceptado);
    }

    private boolean algunaPuntuacionNoAceptada(Partido partido) {
        return partido.getPuntuaciones().stream().anyMatch(p -> !p.isAceptado());
    }
    
    @Override
    public List<Partido> getPartidosHistoricos() {
        return partidoDAO.findAll()
                .stream()
                .filter(this::esAntesDeAhora)
                .filter(this::todasLasPuntuacionesAceptadas)
                .collect(Collectors.toList());
    }

    //para implementar un metodo que borre partidos no validos
    @Override
    public List<Partido> getPartidosHistoricosNoValidos() {
        return partidoDAO.findAll()
                .stream()
                .filter(this::esAntesDeAhora)
                .filter(this::algunaPuntuacionNoAceptada)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Partido> getPartidosValidados() {
        return partidoDAO.findAll()
                .stream()
                .filter(this::esDespuesDeAhora)
                .filter(this::todasLasPuntuacionesAceptadas)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Partido> getPartidosPorConfirmar() {
        return partidoDAO.findAll()
                .stream()
                .filter(this::esDespuesDeAhora)
                .filter(this::algunaPuntuacionNoAceptada)
                .collect(Collectors.toList());
    }

    @Override
    public List<Partido> getPartidosConfirmadosByJugadorYFecha(Long jugadorId, LocalDate localDate) {
        return partidoDAO.getPartidosValidados()
                .stream()
                .filter(partido -> partido.getPuntuaciones()
                                          .stream()
                                          .anyMatch(puntuacion -> puntuacion.getJugador().getId()
                                                                            .equals(jugadorId)))
                .filter(partido -> partido.getCuando()
                                          .toLocalDate()
                                          .equals(localDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Partido> getPartidosConfirmadosByCampoYFechaHora(Long campoId, LocalDateTime localDateTime) {
        return partidoDAO.getPartidosValidados()
                .stream()
                .filter(p -> p.getCuando().equals(localDateTime))
                .filter(p -> p.getCampo().getId().equals(campoId))
                .collect(Collectors.toList());        
    }    
    
}
