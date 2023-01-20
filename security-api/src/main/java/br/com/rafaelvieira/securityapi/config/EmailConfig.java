package br.com.rafaelvieira.securityapi.config;

import br.com.rafaelvieira.securityapi.modules.auth.service.email.EmailService;
import br.com.rafaelvieira.securityapi.modules.auth.service.email.SmtpEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author rafae
 */
@Configuration
@PropertySource("classpath:application.properties")
public class EmailConfig {

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }
}
