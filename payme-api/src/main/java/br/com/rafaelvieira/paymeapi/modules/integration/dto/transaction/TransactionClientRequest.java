package br.com.rafaelvieira.paymeapi.modules.integration.dto.transaction;

import br.com.rafaelvieira.paymeapi.modules.security.dto.JwtResponse;
import br.com.rafaelvieira.paymeapi.modules.transaction.dto.TransactionRequest;
import br.com.rafaelvieira.paymeapi.modules.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rafae
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionClientRequest {

    private static final String PREFIX_BR = "+55";

    @JsonProperty("api_key")
    private String apiKey;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("card_id")
    private String cardId;
    @JsonProperty("customer")
    private ClientRequest customer;
    @JsonProperty("billing")
    private ChangeClientRequest change;
    @JsonProperty("items")
    private List<ItemTransactionClientRequest> items;

    public static TransactionClientRequest convertFrom(JwtResponse user,
                                                       TransactionRequest transactionRequest){
        return TransactionClientRequest.builder()
                .amount(transactionRequest.getAmount())
                .cardId(transactionRequest.getCardId())
                .customer(ClientRequest.convertFrom(user, treatPhoneNumbers(transactionRequest.getPhoneNumbers())))
                .change(ChangeClientRequest.convertFrom(transactionRequest.getChange()))
                .items(transactionRequest.getItems().stream()
                        .map(ItemTransactionClientRequest::convertFrom)
                        .collect(Collectors.toList()))
                .build();
    }

    private static List<String> treatPhoneNumbers(List<String> phoneNumbers){
        return phoneNumbers
                .stream()
                .map(TransactionClientRequest::treatPhoneNumbers)
                .collect(Collectors.toList());
    }

    private static String treatPhoneNumbers(String phoneNumber){
        if(!phoneNumber.contains(PREFIX_BR)){
            return PREFIX_BR.concat(phoneNumber);
        }
        return phoneNumber;
    }

    public String toJson(){
        return JsonUtil.convertObjectToJson(this);
    }

}
