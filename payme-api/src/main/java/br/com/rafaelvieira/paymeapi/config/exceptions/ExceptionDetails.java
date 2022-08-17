package br.com.rafaelvieira.paymeapi.config.exceptions;

import lombok.Data;

/**
 * @author rafae
 */

@Data
public class ExceptionDetails {

    private int status;
    private String message;
}
