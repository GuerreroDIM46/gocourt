package es.mde.goCourt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import es.mde.repositorios.PartidoListener.PartidoMismoHorarioException;
import es.mde.repositorios.PuntuacionListener.JugadorConPartidosElMismoDiaException;


@ControllerAdvice
public class ManejadorGlobalDeExcepciones {
    
    @ExceptionHandler(PartidoMismoHorarioException.class)
    public ResponseEntity<String> manejarPartidoMismoHorarioException(PartidoMismoHorarioException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    
    @ExceptionHandler(JugadorConPartidosElMismoDiaException.class)
    public ResponseEntity<String> manejarJugadorConPartidosElMismoDiaException(JugadorConPartidosElMismoDiaException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}
