package br.com.rafaelvieira.pagarmeapi.config.exceptions;

import lombok.Data;

/**
 * @author rafae
 */

@Data
public class ExceptionDetails {

    private int status;
    private String message;
}
