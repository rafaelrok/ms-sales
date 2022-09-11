package br.com.rafaelvieira.paymeapi.modules.transaction.dto;

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
public class ItemTransactionRequest {

    private String id;
    private String title;
    private BigDecimal unitPrice;
    private Long quantity;
}

