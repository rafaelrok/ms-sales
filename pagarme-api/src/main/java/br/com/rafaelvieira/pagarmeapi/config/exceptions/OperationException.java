package br.com.rafaelvieira.pagarmeapi.config.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author rafae
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class OperationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OperationException(String message) {
        super(message);
    }
}
