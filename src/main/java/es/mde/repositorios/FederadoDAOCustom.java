package es.mde.repositorios;

import java.util.List;
import es.mde.entidades.Federado;
import es.mde.entidades.Jugador;

public interface FederadoDAOCustom {
    List<Federado> getFederadosSinPaginacion();
    
    List<Federado> encontrarFederadosParaCompetir(Long jugadorId, float puntuacionMinima, float rangoInferior, float rangoSuperior);
    
}
