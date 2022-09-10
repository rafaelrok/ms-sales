package br.com.rafaelvieira.paymeapi.modules.card.dto;

import br.com.rafaelvieira.paymeapi.modules.card.model.Card;
import br.com.rafaelvieira.paymeapi.modules.utils.JsonUtil;
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
public class CardResponse {

    private Integer id;

    private String cardId;

    private String userId;

    private String brand;

    private String lastDigits;

    private String country;

    public static CardResponse convertFrom(Card card) {
        var response = new CardResponse();
        BeanUtils.copyProperties(card, response);
        return response;
    }

    public String toJson() {
        return JsonUtil.convertObjectToJson(this);
    }
}
