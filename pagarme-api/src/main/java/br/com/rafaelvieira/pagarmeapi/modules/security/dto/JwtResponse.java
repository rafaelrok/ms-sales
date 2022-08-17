package br.com.rafaelvieira.pagarmeapi.modules.security.dto;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.rafaelvieira.pagarmeapi.modules.security.util.JwtFieldUtil.getField;
import static br.com.rafaelvieira.pagarmeapi.modules.security.util.JwtFieldUtil.getFieldId;

/**
 * @author rafae
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String id;
    private String name;
    private String email;
    private String cpf;

    public static JwtResponse of(Claims dataUser) {
        return JwtResponse
                .builder()
                .id(getFieldId(dataUser))
                .name(getField("name", dataUser))
                .email(getField("email", dataUser))
                .cpf(getField("cpf", dataUser))
                .build();
    }
}
