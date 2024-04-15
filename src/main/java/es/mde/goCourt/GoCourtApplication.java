package es.mde.goCourt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GoCourtApplication {

    private static final Logger log = LoggerFactory.getLogger(GoCourtApplication.class);

    public static void main(String[] args) {

        ConfigurableApplicationContext context =
        SpringApplication.run(GoCourtApplication.class, args);

        System.err.println("Está funcionando la aplicación");
        log.debug("Está funcionando la aplicación");
        
//        context.close();
//        System.err.println("Está cerrándose la aplicación");

    }

}
