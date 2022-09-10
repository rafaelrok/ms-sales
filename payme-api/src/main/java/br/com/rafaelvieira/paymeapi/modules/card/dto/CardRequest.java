package br.com.rafaelvieira.paymeapi.modules.card.dto;

import br.com.rafaelvieira.paymeapi.modules.utils.JsonUtil;
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
public class CardRequest {

    private String cardExpirationDate;
    private String cardNumber;
    private String cardCvv;
    private String cardHolderName;

    public String toJson() {
        return JsonUtil.convertObjectToJson(this);
    }
}
