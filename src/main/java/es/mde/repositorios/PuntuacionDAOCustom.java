package es.mde.repositorios;

public interface PuntuacionDAOCustom {
	
	String actualizarAsistencia(Long id, boolean aceptado);
	
	String actualizarCompartidoTelefono(Long id, boolean compartidoTelefono);
	
}
