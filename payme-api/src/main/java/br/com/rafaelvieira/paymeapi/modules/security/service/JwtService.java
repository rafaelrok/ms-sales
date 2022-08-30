package br.com.rafaelvieira.paymeapi.modules.security.service;

import br.com.rafaelvieira.paymeapi.config.exceptions.AuthenticationException;
import br.com.rafaelvieira.paymeapi.modules.security.dto.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static br.com.rafaelvieira.paymeapi.modules.utils.RequestUtil.getCurrentRequest;
import static br.com.rafaelvieira.paymeapi.modules.utils.TokenUtil.extractTokenRequest;
import static org.springframework.util.ObjectUtils.isEmpty;


/**
 * @author rafae
 */
@Slf4j
@Service
public class JwtService {

    private static final String EMPTY_SPACE = " ";
    private static final Integer TOKEN_INDEX = 1;
    private static final AuthenticationException TOKEN_ACESSO_INVALIDO
            = new AuthenticationException("Invalid access token.");

    @Value("${jwt.secret}")
    private String secret;

    public boolean hasAuthenticatedUser(String token) {
        var user = retrieveUserDataFromToken(token);
        return !isEmpty(user);
    }

    public JwtResponse retrieveAuthenticatedUser() {
        var token = extractTokenRequest(getCurrentRequest());
        return retrieveUserDataFromToken(token);
    }

    public JwtResponse retrieveUserDataFromToken(String jwt) {
        try {
            return JwtResponse.of(decryptJwt(jwt).getBody());
        } catch (Exception ex) {
            log.error("Error processing access token: ".concat(jwt).concat(". Error: "), ex);
            throw TOKEN_ACESSO_INVALIDO;
        }
    }

    private Jws<Claims> decryptJwt(String jwt) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(jwt);
        } catch (Exception ex) {
            log.error("Error processing access token: ".concat(jwt).concat(". Error: "), ex);
            throw TOKEN_ACESSO_INVALIDO;
        }
    }

    public void validateAuthorization(String token) {
        var accessToken = extractToken(token);
        try {
            var claims = Jwts
                    .parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            var user = JwtResponse.of(claims);
            if (isEmpty(user) || isEmpty(user.getId())) {
                throw new AuthenticationException("The user is not valid.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Error while trying to process the Access Token.");
        }
    }

    private String extractToken(String token) {
        if (isEmpty(token)) {
            throw new AuthenticationException("The access token was not informed.");
        }
        if (token.contains(EMPTY_SPACE)) {
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }
        return token;
    }
}
