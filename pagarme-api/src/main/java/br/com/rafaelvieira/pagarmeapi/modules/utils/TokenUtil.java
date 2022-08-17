package br.com.rafaelvieira.pagarmeapi.modules.utils;

import br.com.rafaelvieira.pagarmeapi.config.exceptions.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @author rafae
 */

public class TokenUtil {

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String BEARER = "bearer";
    private static final String EMPTY_SPACE = " ";
    private static final String EMPTY = "";
    private static final Integer TOKEN_INDEX = 1;
    private static final Integer TOKEN_INITIAL_INDEX = 0;
    private static final Integer TOKEN_BEARER_INDEX = 7;

    public static String extractTokenRequest(HttpServletRequest request) {
        var accessToken = request.getHeader(AUTHORIZATION_HEADER);
        if (isEmpty(accessToken)) {
            throw new AuthenticationException("Token de acesso não informado.");
        }
        if (accessToken.toLowerCase().contains(BEARER) && accessToken.contains(EMPTY_SPACE)) {
            return accessToken.split(EMPTY_SPACE)[TOKEN_INDEX];
        }
        if (accessToken.toLowerCase().contains(BEARER) && !accessToken.contains(EMPTY_SPACE)) {
            return accessToken.substring(TOKEN_INITIAL_INDEX, TOKEN_BEARER_INDEX);
        }
        return accessToken;
    }
}