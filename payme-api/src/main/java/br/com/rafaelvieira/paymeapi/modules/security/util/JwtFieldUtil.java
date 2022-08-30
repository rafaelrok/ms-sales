package br.com.rafaelvieira.paymeapi.modules.security.util;

import br.com.rafaelvieira.paymeapi.config.exceptions.ValidationException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rafae
 */
@Slf4j
public class JwtFieldUtil {

    public static String getFieldId(Claims claims) {
        try {
            return (String) claims.get("id");
        } catch (Exception ex) {
            log.error("Error trying to retrieve JWT ID: {}", claims.toString());
            throw new ValidationException("Error trying to retrieve Token ID field.");
        }
    }

    public static String getField(String field, Claims claims) {
        try {
            return (String) claims.get(field);
        } catch (Exception ex) {
            log.error("Error trying to retrieve field {} from JWT: {}", field, claims.toString());
            throw new ValidationException(
                    String.format("Error trying to retrieve Token field %s.", field)
            );
        }
    }
}
