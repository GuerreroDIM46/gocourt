package es.mde.repositorios;

import java.util.List;

import es.mde.entidades.Partido;

public interface PartidoDAOCustom {
	
	List<Partido> getPartidosHistoricos();
	
	List<Partido> getPartidosHistoricosNoValidos();
	
	List<Partido> getPartidosValidados();
	
	List<Partido> getPartidosPorConfirmar();

}
