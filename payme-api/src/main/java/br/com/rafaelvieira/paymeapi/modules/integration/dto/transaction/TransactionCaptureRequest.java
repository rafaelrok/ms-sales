package br.com.rafaelvieira.paymeapi.modules.integration.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author rafae
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCaptureRequest {

    @JsonProperty("api_key")
    private String apiKey;
    @JsonProperty("amount")
    private BigDecimal amount;

    public static TransactionCaptureRequest create(String apiKey,
                                                   BigDecimal amount) {
        return TransactionCaptureRequest.builder()
                .apiKey(apiKey)
                .amount(amount)
                .build();
    }
}
