package br.com.rafaelvieira.paymeapi.modules.integration.dto.card;

import br.com.rafaelvieira.paymeapi.modules.utils.JsonUtil;
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
public class CardClientResponse {

    private String id;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("last_digits")
    private String lastDigits;

    @JsonProperty("country")
    private String country;

    public String toJson() {
        return JsonUtil.convertObjectToJson(this);
    }
}
