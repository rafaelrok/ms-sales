package br.com.rafaelvieira.paymeapi.modules.integration.dto.transaction;

import br.com.rafaelvieira.paymeapi.modules.security.dto.JwtResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

import static br.com.rafaelvieira.paymeapi.modules.utils.Constants.COUNTRY_BRASIL;
import static br.com.rafaelvieira.paymeapi.modules.utils.Constants.INDIVIDUAL_TYPE;

/**
 * @author rafae
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("country")
    private String country;

    private String email;

    @JsonProperty("documents")
    private List<DocumentClientRequest> documents;

    @JsonProperty("phone_numbers")
    private List<String> phoneNumbers;

    public static ClientRequest convertFrom(JwtResponse user, List<String> phoneNumbers) {
        return ClientRequest
                .builder()
                .externalId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .documents(Collections.singletonList(DocumentClientRequest.convertFrom(user.getCpf())))
                .type(INDIVIDUAL_TYPE)
                .country(COUNTRY_BRASIL)
                .phoneNumbers(phoneNumbers)
                .build();
    }
}
