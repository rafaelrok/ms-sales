package br.com.rafaelvieira.paymeapi.modules.transaction.dto;


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
public class ChangeRequest {

    private Integer id;
    private String name;
    private String cpfCnpj;
    private AddressChangeRequest address;
}
