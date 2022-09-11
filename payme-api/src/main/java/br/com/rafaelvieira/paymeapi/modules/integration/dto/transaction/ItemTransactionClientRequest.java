package br.com.rafaelvieira.paymeapi.modules.integration.dto.transaction;

import br.com.rafaelvieira.paymeapi.modules.transaction.dto.ItemTransactionRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @author rafae
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemTransactionClientRequest {

    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("unit_price")
    private BigDecimal unitPrice;
    @JsonProperty("quantity")
    private Long quantity;
    @JsonProperty("tangible")
    private Boolean tangible;

    public static ItemTransactionClientRequest convertFrom(ItemTransactionRequest itemTransactionRequest) {
        var itemTransactionClientRequest = new ItemTransactionClientRequest();
        BeanUtils.copyProperties(itemTransactionRequest, itemTransactionClientRequest);
        itemTransactionClientRequest.setTangible(false);
        return itemTransactionClientRequest;
    }
}
