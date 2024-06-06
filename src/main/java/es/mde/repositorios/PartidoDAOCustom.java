package es.mde.repositorios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import es.mde.entidades.Campo;
import es.mde.entidades.Jugador;
import es.mde.entidades.Partido;

public interface PartidoDAOCustom {
	
	List<Partido> getPartidosHistoricos();
	
	List<Partido> getPartidosHistoricosNoValidos();
	
	List<Partido> getPartidosValidados();
	
	List<Partido> getPartidosPorConfirmar();
	
	List<Partido> getPartidosByJugadorYFecha(Jugador jugador, LocalDate localDate);
	
	List<Partido> getPartidosByCampoYFechaHora(Long campoId, LocalDateTime localDateTime);

}
