package es.mde.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mde.entidades.Partido;
import es.mde.entidades.Puntuacion;
import es.mde.repositorios.PartidoDAO;
import es.mde.repositorios.PuntuacionDAO;
import es.mde.services.EmailService;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/email")
public class EmailController {
	
	
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private PartidoDAO partidoDAO;

    @Autowired
    private PuntuacionDAO puntuacionDAO;

    @PostMapping("/sendEmailDePrueba")
    public String sendEmailDePrueba() {
        String to = "sirxavor@gmail.com"; // Cambia esto al correo de destino para la prueba
        String subject = "Correo de Prueba";
        String templateName = "registration"; 

        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "majo");

        try {
            emailService.sendHtmlEmail(to, subject, templateName, variables);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error al enviar el correo: " + e.getMessage();
        }
        
        return "Correo de prueba enviado con éxito";
    }
    
    @PostMapping("/sendComunicadoAsignacionDePartido")
    public String sendComunicadoAsignacionDePartido(@RequestBody Map<String, Long> ids) {
        try {
            // Obtener datos de las entidades proporcionadas
            Partido partido = partidoDAO.findById(ids.get("partidoId")).orElse(null);
            Puntuacion puntuacion1 = puntuacionDAO.findById(ids.get("puntuacion1Id")).orElse(null);
            Puntuacion puntuacion2 = puntuacionDAO.findById(ids.get("puntuacion2Id")).orElse(null);

            if (partido == null || puntuacion1 == null || puntuacion2 == null) {
                return "Error: No se encontraron algunas entidades.";
            }

            String campo = partido.getNombreCampo();
            LocalDateTime fechaHora = partido.getCuando();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            String fecha = fechaHora.toLocalDate().format(dateFormatter);
            String hora = fechaHora.toLocalTime().format(timeFormatter);

            String nombreJugador1 = puntuacion1.getJugador().getNombre();
            String jugador2 = puntuacion2.getNombreCompleto();  
            String emailJugador1 = puntuacion1.getJugador().getEmail();

            Map<String, Object> variables = new HashMap<>();
            variables.put("campo", campo);
            variables.put("fecha", fecha);
            variables.put("hora", hora);
            variables.put("jugador1", nombreJugador1);
            variables.put("jugador2", jugador2);

//            String to = emailJugador1;
            String to = "sirxavor@gmail.com";
            String subject = "Comunicado de Asignación de Partido";
            String templateName = "asignacionPartido"; 

            emailService.sendHtmlEmail(to, subject, templateName, variables);

            return "Correo de asignación de partido enviado con éxito";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
   
   
}