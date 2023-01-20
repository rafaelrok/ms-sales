package br.com.rafaelvieira.securityapi.modules.auth.service.email;

import br.com.rafaelvieira.securityapi.modules.auth.model.TokenValidation;
import br.com.rafaelvieira.securityapi.modules.auth.model.User;

import javax.mail.internet.MimeMessage;

/**
 * @author rafae
 */
public interface EmailService {

    void sendHtmlEmail(MimeMessage msg);
    void sendConfirmationHtmlEmail(User user, TokenValidation vToken);
}
