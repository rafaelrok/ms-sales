package br.com.rafaelvieira.productapi.config;

import br.com.rafaelvieira.productapi.config.exception.ValidationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author rafae
 */
public class RequestUtil {

    public static HttpServletRequest getCurrentRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes())
                    .getRequest();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ValidationException("The current request could not be processed.");
        }
    }
}