package br.com.rafaelvieira.paymeapi.modules.transaction.dto;

import br.com.rafaelvieira.paymeapi.modules.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author rafae
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    private BigDecimal amount;
    private String cardId;
    private List<String> phoneNumbers;
    private ChangeRequest change;
    private List<ItemTransactionRequest> items;

    public String toJson(){
        return JsonUtil.convertObjectToJson(this);
    }
}
