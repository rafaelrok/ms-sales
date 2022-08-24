package br.com.rafaelvieira.actuatorapi;

;import br.com.rafaelvieira.actuatorapi.config.WebSecurityConfig;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author rafae
 * @version 1.0
 */

@Configuration
@EnableAutoConfiguration
@EnableAdminServer
@Import({WebSecurityConfig.class})
public class ActuatorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActuatorApiApplication.class, args);
    }
}
