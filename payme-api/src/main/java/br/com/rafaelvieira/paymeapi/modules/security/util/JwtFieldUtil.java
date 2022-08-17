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
            log.error("Erro ao tentar recuperar ID do JWT: {}", claims.toString());
            throw new ValidationException("Erro ao tentar recuperar o campo ID do Token.");
        }
    }

    public static String getField(String field, Claims claims) {
        try {
            return (String) claims.get(field);
        } catch (Exception ex) {
            log.error("Erro ao tentar recuperar o campo {} do JWT: {}", field, claims.toString());
            throw new ValidationException(
                    String.format("Erro ao tentar recuperar o campo %s do Token.", field)
            );
        }
    }
}
