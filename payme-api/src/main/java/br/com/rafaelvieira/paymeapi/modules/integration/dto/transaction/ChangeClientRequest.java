package br.com.rafaelvieira.paymeapi.modules.integration.dto.transaction;


import br.com.rafaelvieira.paymeapi.modules.transaction.dto.ChangeRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rafae
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeClientRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("address")
    private AddressChangeClientRequest address;

    public static ChangeClientRequest convertFrom(ChangeRequest changeRequest) {
        return ChangeRequest
                .builder()
                .name(changeRequest.getName())
                .address(AddressChangeClientRequest.convertFrom(changeRequest.getAddress()))
                .build();
    }
}
