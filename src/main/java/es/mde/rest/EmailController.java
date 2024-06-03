package es.mde.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import es.mde.services.EmailService;
import es.mde.services.EmailInicializerService;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/email")
public class EmailController {	

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private EmailInicializerService preparacionVariablesEmailService;

    @PostMapping("/sendComunicadoAsignacionDePartido")
    public String sendComunicadoAsignacionDePartido(@RequestBody Map<String, Long> ids) {
        try {
            Long partidoId = ids.get("partidoId");
            Long puntuacion1Id = ids.get("puntuacion1Id");
            Long puntuacion2Id = ids.get("puntuacion2Id");

            Map<String, Object> variables = preparacionVariablesEmailService.prepareVariablesForAsignacionDePartido(partidoId, puntuacion1Id, puntuacion2Id);
            if (variables == null) {
                return "Error: No se encontraron algunas entidades.";
            }

            String to = (String) variables.get("emailJugador1");
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