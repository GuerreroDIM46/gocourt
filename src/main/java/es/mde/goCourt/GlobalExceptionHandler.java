package es.mde.goCourt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import es.mde.services.PartidoService.PartidoMismoHorarioException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(PartidoMismoHorarioException.class)
    public ResponseEntity<String> handlePartidoMismoHorarioException(PartidoMismoHorarioException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

}
