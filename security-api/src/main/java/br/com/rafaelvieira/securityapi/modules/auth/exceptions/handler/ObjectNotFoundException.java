package br.com.rafaelvieira.securityapi.modules.auth.exceptions.handler;

import java.io.Serializable;

/**
 * @author rafae
 */
public class ObjectNotFoundException extends RuntimeException implements Serializable  {

    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException (String message) {
        super(message);
    }
}
