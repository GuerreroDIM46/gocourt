package es.mde.repositorios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import es.mde.entidades.Partido;

public interface PartidoDAOCustom {
	
	List<Partido> getPartidosHistoricos();
	
	List<Partido> getPartidosHistoricosNoValidos();
	
	List<Partido> getPartidosValidados();
	
	List<Partido> getPartidosPorConfirmar();
	
	List<Partido> getPartidosConfirmadosByJugadorYFecha(Long jugadorId, LocalDate localDate);
	
	List<Partido> getPartidosConfirmadosByCampoYFechaHora(Long campoId, LocalDateTime localDateTime);
	
}
