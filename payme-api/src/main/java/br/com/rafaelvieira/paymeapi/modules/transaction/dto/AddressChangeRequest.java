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
public class AddressChangeRequest {

    private String country;
    private String state;
    private String city;
    private String neighborhood;
    private String street;
    private String streetNumber;
    private String complement;
    private String zipcode;
}
