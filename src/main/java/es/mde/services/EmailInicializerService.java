package es.mde.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.mde.entidades.Partido;
import es.mde.entidades.Puntuacion;
import es.mde.repositorios.PartidoDAO;
import es.mde.repositorios.PuntuacionDAO;
import jakarta.mail.MessagingException;

@Service
public class EmailInicializerService {
    
    @Autowired
    private PartidoDAO partidoDAO;

    @Autowired
    private PuntuacionDAO puntuacionDAO;
    
    @Autowired
    private EmailService emailService;
    
    @Value("${api.direccion}")
    private String direccionAPI;

    public Map<String, Object> prepareVariablesForAsignacionDePartido(Long partidoId, Long puntuacion1Id, Long puntuacion2Id) {
        Partido partido = partidoDAO.findById(partidoId).orElse(null);
        Puntuacion puntuacion1 = puntuacionDAO.findById(puntuacion1Id).orElse(null);
        Puntuacion puntuacion2 = puntuacionDAO.findById(puntuacion2Id).orElse(null);

        if (partido == null || puntuacion1 == null || puntuacion2 == null) {
            return null;
        }
        
        return obtenerVariablesEmail(partido, puntuacion1, puntuacion2);
    }
    
    public Map<String, Object> obtenerVariablesEmail(Partido Partido, Puntuacion Puntuacion1, Puntuacion Puntuacion2){
        Partido partido = Partido;
        Puntuacion puntuacion1 = Puntuacion1;
        Puntuacion puntuacion2 = Puntuacion2;       
        
        Long puntuacion1Id = puntuacion1.getId();
        Long puntuacion2Id = puntuacion2.getId();
        String campo = partido.getNombreCampo();
        LocalDateTime fechaHora = partido.getCuando();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String fecha = fechaHora.toLocalDate().format(dateFormatter);
        String hora = fechaHora.toLocalTime().format(timeFormatter);        
        String nombreJugador1 = puntuacion1.getJugador().getNombre();
        String jugador2 = puntuacion2.getNombreCompleto();
        String aceptarInvitacionUrl = direccionAPI + "puntuaciones/search/actualizarAsistencia?id=" + puntuacion1Id + "&aceptado=true";
        String rechazarInvitacionUrl = direccionAPI + "puntuaciones/search/actualizarAsistencia?id=" + puntuacion1Id + "&aceptado=false";
        String aceptarIntercambioUrl = direccionAPI;
        String rechazarIntercambioUrl = direccionAPI;
        

        Map<String, Object> variables = new HashMap<>();
        variables.put("campo", campo);
        variables.put("fecha", fecha);
        variables.put("hora", hora);
        variables.put("jugador1", nombreJugador1);
        variables.put("jugador2", jugador2);
        variables.put("aceptarInvitacion", aceptarInvitacionUrl);
        variables.put("rechazarInvitacion", rechazarInvitacionUrl);
        variables.put("aceptarIntercambio", aceptarIntercambioUrl);
        variables.put("rechazarIntercambio", rechazarIntercambioUrl);

        return variables;
    }

    public void inicializarSugerencia(Long id) {
        Puntuacion puntuacion1 = puntuacionDAO.findById(id).orElse(null);
        Partido partido = puntuacion1.getPartido();
        Puntuacion puntuacion2 = partido.getPuntuaciones()
                                        .stream()
                                        .filter(p -> !p.equals(puntuacion1))
                                        .findFirst()
                                        .orElse(null);

        enviarEmailSugerencia(partido, puntuacion1, puntuacion2);
        enviarEmailSugerencia(partido, puntuacion2, puntuacion1);
    }

    private void enviarEmailSugerencia(Partido partido, Puntuacion puntuacion1, Puntuacion puntuacion2) {
        try {
            Map<String, Object> variables = obtenerVariablesEmail(partido, puntuacion1, puntuacion2);
            String to = (String) variables.get("emailJugador1");
            String subject = "Comunicado de Asignación de Partido";
            String templateName = "sugerenciaCompartirTelefono";

            emailService.sendHtmlEmail(to, subject, templateName, variables);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

            
    
    
}
