package br.com.rafaelvieira.paymeapi.modules.integration.dto.card;

import br.com.rafaelvieira.paymeapi.modules.card.dto.CardRequest;
import br.com.rafaelvieira.paymeapi.modules.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author rafae
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardClientRequest {

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("card_expiration_date")
    private String cardExpirationDate;

    @JsonProperty("card_number")
    private String cardNumber;

    @JsonProperty("card_cvv")
    private String cardCvv;

    @JsonProperty("card_holder_name")
    private String cardHolderName;

    public static CardClientRequest convertFrom(CardRequest request) {
        var clientRequest = new CardClientRequest();
        BeanUtils.copyProperties(request, clientRequest);
        return clientRequest;
    }

    public String toJson() {
        return JsonUtil.convertObjectToJson(this);
    }
}
