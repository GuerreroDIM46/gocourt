package es.mde.goCourt;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import es.mde.entidades.Federado.HandicapFueraDeRangoException;
import es.mde.entidades.Partido.AntesDeHoyExcepcion;
import es.mde.entidades.Partido.FueraRangoHorarioException;
import es.mde.entidades.Principiante.PuntuacionFueraDeRangoException;
import es.mde.repositorios.FederadoDAOImpl.JugadorNoEncontradoException;
import es.mde.repositorios.PartidoListener.PartidoMismoHorarioException;
import es.mde.repositorios.PuntuacionListener.JugadorConPartidosElMismoDiaException;
import jakarta.validation.ConstraintViolationException;


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
    
    @ExceptionHandler(JugadorNoEncontradoException.class)
    public ResponseEntity<String> manejarJugadorNoEncontradoException(JugadorNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    
    @ExceptionHandler(PuntuacionFueraDeRangoException.class)
    public ResponseEntity<String> manejarPuntuacionFueraDeRangoException(PuntuacionFueraDeRangoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    
    @ExceptionHandler(HandicapFueraDeRangoException.class)
    public ResponseEntity<String> manejarHandicapFueraDeRangoException(HandicapFueraDeRangoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> errors.put(cv.getPropertyPath().toString(), cv.getMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(AntesDeHoyExcepcion.class)
    public ResponseEntity<String> manejarAntesDeHoyExcepcion(AntesDeHoyExcepcion ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    
    @ExceptionHandler(FueraRangoHorarioException.class)
    public ResponseEntity<String> manejarFueraRangoHorarioException(FueraRangoHorarioException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


}
