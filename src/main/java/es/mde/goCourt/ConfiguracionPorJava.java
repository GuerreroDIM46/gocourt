package es.mde.goCourt;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.mde.rest.ConfiguracionRest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@Configuration
@PropertySource({ 
    "classpath:config/rest.properties", 
    "classpath:config/jackson.properties" })
@EnableTransactionManagement
@EnableJpaRepositories("${misRepositorios}")
@ComponentScan({"es.mde.services","es.mde.repositorios", "es.mde.rest"})//para que escanee los services...

@Import(ConfiguracionRest.class)
public class ConfiguracionPorJava {
    
    @Value("${misEntidades}")
    String entidades;
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env,
            JpaVendorAdapter vendorAdapter) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setJpaVendorAdapter(vendorAdapter);

        em.setPackagesToScan(entidades); 

        Properties jpaProperties = new Properties()

;
        Arrays.asList("dialect", "show-sql", "ddl-auto", "hbm2ddl.auto", "enable_lazy_load_no_trans") //  leer valor de para las entidades anotadas 
                .stream().map(s -> "hibernate." + s)
                .map(p -> new AbstractMap.SimpleEntry<String, String>(p, env.getProperty(p)))
                .filter(e -> e.getValue() != null).forEach(e -> jpaProperties.put(e.getKey(), e.getValue()));
        em.setJpaProperties(jpaProperties);
        
        

        return em;
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory emf) {
        System.err.println("--- LAS ENTIDADES MAPEADAS SON ---");
        emf.getMetamodel().getEntities().forEach(System.err::println);
        System.err.println("----------------------------------");

        return emf.createEntityManager();
    }
    
    @Bean
    public ObjectMapper getObjectMapper() {

        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

}