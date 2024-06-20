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
    PartidoDAO partidoDAO;

    @Autowired
    PuntuacionDAO puntuacionDAO;
    
    @Autowired
    EmailService emailService;
    
    @Value("${api.direccion}")
    private String direccionAPI;
    
    @Value("${app.direccion}")
    private String direccionApp;

    public Map<String, Object> obtenerVariablesEmail(Partido Partido, Puntuacion Puntuacion1, Puntuacion Puntuacion2){
        Partido partido = Partido;
        Puntuacion puntuacion1 = Puntuacion1;
        Puntuacion puntuacion2 = Puntuacion2;       
        Long partidoId = partido.getId();
        Long puntuacion1Id = puntuacion1.getId();
        String campo = partido.getNombreCampo();
        LocalDateTime fechaHora = partido.getCuando();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String fecha = fechaHora.toLocalDate().format(dateFormatter);
        String partidoToken = partido.getToken();
        String hora = fechaHora.toLocalTime().format(timeFormatter);        
        String nombreJugador1 = puntuacion1.getJugador().getNombre();
        String nombreCompletoJugador1 = puntuacion1.getNombreCompleto();
        String jugador2 = puntuacion2.getNombreCompleto();
        String emailJugador1 = Puntuacion1.getJugador().getEmail();
        String telefonoJugador2 = puntuacion2.getJugador().getTelefono();
        String aceptarInvitacionUrl = direccionAPI + "puntuaciones/search/actualizarAsistencia?id=" + puntuacion1Id + "&aceptado=true";
        String rechazarInvitacionUrl = direccionAPI + "puntuaciones/search/actualizarAsistencia?id=" + puntuacion1Id + "&aceptado=false";
        String aceptarIntercambioUrl = direccionAPI + "puntuaciones/search/actualizarCompartidoTelefono?id=" + puntuacion1Id + "&compartidoTelefono=true";
        String rechazarIntercambioUrl = direccionAPI + "puntuaciones/search/actualizarCompartidoTelefono?id=" + puntuacion1Id + "&compartidoTelefono=false";
        String introducirDetallesPartidoURL = direccionApp + "#/landingPage/" + partidoToken;

        Map<String, Object> variables = new HashMap<>();
        variables.put("emailJugador1", emailJugador1);
        variables.put("campo", campo);
        variables.put("fecha", fecha);
        variables.put("hora", hora);
        variables.put("jugador1", nombreJugador1);
        variables.put("nombreCompletoJugador1", nombreCompletoJugador1);
        variables.put("jugador2", jugador2);
        variables.put("telefonoJugador2", telefonoJugador2);
        variables.put("aceptarInvitacion", aceptarInvitacionUrl);
        variables.put("rechazarInvitacion", rechazarInvitacionUrl);
        variables.put("aceptarIntercambio", aceptarIntercambioUrl);
        variables.put("rechazarIntercambio", rechazarIntercambioUrl);
        variables.put("introducirDetallesPartido", introducirDetallesPartidoURL);
        return variables;
    }
    
    public String enviarComunicadoAsignacionServicio(Long partidoId, Long puntuacion1Id, Long puntuacion2Id) {
        Partido partido = partidoDAO.findById(partidoId).orElse(null);
        Puntuacion puntuacion1 = puntuacionDAO.findById(puntuacion1Id).orElse(null);
        Puntuacion puntuacion2 = puntuacionDAO.findById(puntuacion2Id).orElse(null);
        String retorno;
        try {
            Map<String, Object> variables = obtenerVariablesEmail(partido, puntuacion1, puntuacion2);
            String to = (String) variables.get("emailJugador1");
            String subject = "Comunicado de Asignación de Partido";
            String templateName = "asignacionPartido";

            emailService.sendHtmlEmail(to, subject, templateName, variables);
            retorno = "Mensaje enviado correctamente";
        } catch (MessagingException e) {
            e.printStackTrace();
            retorno = "Error al enviar el correo: " + e.getMessage();
        }
        return retorno;
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

    public void enviarEmailSugerencia(Partido partido, Puntuacion puntuacion1, Puntuacion puntuacion2) {
        try {
            Map<String, Object> variables = obtenerVariablesEmail(partido, puntuacion1, puntuacion2);
            String to = (String) variables.get("emailJugador1");
            String subject = "Solicitud de intercambio de numeros de telefono";
            String templateName = "sugerenciaCompartirTelefono";

            emailService.sendHtmlEmail(to, subject, templateName, variables);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    } 
    
    public void inicializarEmailCompartirTelefono(Partido partido, Puntuacion puntuacion1, Puntuacion puntuacion2) {
        enviarEmailCompartirTelefono(partido, puntuacion1, puntuacion2);
        enviarEmailCompartirTelefono(partido, puntuacion2, puntuacion1);
    }

    private void enviarEmailCompartirTelefono(Partido partido, Puntuacion puntuacion1, Puntuacion puntuacion2) {
        try {
            Map<String, Object> variables = obtenerVariablesEmail(partido, puntuacion1, puntuacion2);
            String to = (String) variables.get("emailJugador1");
            String subject = "Envio de numeros de telefono contendientes de golf";
            String templateName = "envioTelefonoMovil";

            emailService.sendHtmlEmail(to, subject, templateName, variables);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
    }
    
    public String inicializarEmailEnvioPartidoAceptado(Partido partido, Puntuacion puntuacion1, Puntuacion puntuacion2) {
        String retorno1 =  enviarEmailEnvioPartidoAceptado(partido, puntuacion1, puntuacion2);
        String retorno2 =  enviarEmailEnvioPartidoAceptado(partido, puntuacion2, puntuacion1);
        return (retorno1.equals("OK") && retorno2.equals("OK")) ? "Mensajes enviados correctamente" : retorno1 + "\n" + retorno2;

    }
    
    private String enviarEmailEnvioPartidoAceptado(Partido partido, Puntuacion puntuacion1, Puntuacion puntuacion2) {
        String retorno;
        try {
            Map<String, Object> variables = obtenerVariablesEmail(partido, puntuacion1, puntuacion2);
            String to = (String) variables.get("emailJugador1");
            String subject = "Partido Aceptado";
            String templateName = "envioPartidoAceptado";
            emailService.sendHtmlEmail(to, subject, templateName, variables);
            retorno = "OK";
        } catch (MessagingException e) {
            e.printStackTrace();
            retorno = "Error al enviar el correo: " + e.getMessage();
        }
        return retorno;
    }
    
    
}
