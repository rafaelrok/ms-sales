package br.com.rafaelvieira.securityapi.modules.auth.exceptions.handler;

import java.io.Serializable;

/**
 * @author rafae
 */
public class ObjectAlreadyExistException extends RuntimeException implements Serializable  {

    private static final long serialVersionUID = 1L;

    public ObjectAlreadyExistException(String message) {
        super(message);
    }
}
