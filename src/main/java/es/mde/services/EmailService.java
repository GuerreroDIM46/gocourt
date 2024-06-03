package es.mde.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import es.mde.entidades.Puntuacion;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    private final String fromAddress = "xavor@hotmail.es"; // Asegúrate de que esta es la dirección autenticada

    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> variables) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariables(variables);
        String htmlContent = templateEngine.process(templateName, context);

        helper.setTo(to);
        helper.setFrom(fromAddress); // Establece la dirección de remitente
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }
    
    public void enviarTelefonoYEnlaceFormulario() {
        
    }

    

    
    
}