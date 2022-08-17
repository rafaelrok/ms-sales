package br.com.rafaelvieira.pagarmeapi.modules.utils;

import br.com.rafaelvieira.pagarmeapi.config.exceptions.ValidationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static HttpServletRequest getCurrentRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes())
                    .getRequest();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ValidationException("Erro ao tentar recuperar o request atual.");
        }
    }
}
