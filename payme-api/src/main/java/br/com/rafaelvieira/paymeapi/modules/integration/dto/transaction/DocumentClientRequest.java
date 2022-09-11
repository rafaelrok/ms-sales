package br.com.rafaelvieira.paymeapi.modules.integration.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.com.rafaelvieira.paymeapi.modules.utils.Constants.TYPE_CPF;

/**
 * @author rafae
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentClientRequest {

    @JsonProperty("type")
    private String type;

    @JsonProperty("number")
    private String numberDocument;

    public static DocumentClientRequest convertFrom(String document) {
        return DocumentClientRequest
                .builder()
                .type(TYPE_CPF)
                .numberDocument(document)
                .build();
    }
}
