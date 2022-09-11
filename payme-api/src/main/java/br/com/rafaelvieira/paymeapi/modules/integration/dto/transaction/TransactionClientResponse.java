package br.com.rafaelvieira.paymeapi.modules.integration.dto.transaction;

import br.com.rafaelvieira.paymeapi.modules.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TransactionClientResponse {

    private Long id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("status")
    private String status;

    @JsonProperty("authorization_code")
    private String authorizationCode;

    @JsonProperty("acquirer_id")
    private String acquirerId;

    @JsonProperty("acquirer_name")
    private String acquirerName;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("authorized_amount")
    private BigDecimal authorizedAmount;

    @JsonProperty("paid_amount")
    private BigDecimal paidAmount;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("refuse_reason")
    private String recusaReason;

    @JsonProperty("status_reason")
    private String statusReason;

    @JsonProperty("capture_method")
    private String captureMethod;

    @JsonIgnore
    public String getIdStr() {
        return String.valueOf(id);
    }

    public String toJson() {
        return JsonUtil.convertObjectToJson(this);
    }
}
