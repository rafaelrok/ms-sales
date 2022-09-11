package br.com.rafaelvieira.paymeapi.modules.integration.dto.transaction;

import br.com.rafaelvieira.paymeapi.modules.transaction.dto.AddressChangeRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;

import java.util.regex.Pattern;

import static br.com.rafaelvieira.paymeapi.modules.utils.Constants.*;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * @author rafae
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressChangeClientRequest {

    @JsonProperty("country")
    private String country;

    @JsonProperty("state")
    private String state;

    @JsonProperty("city")
    private String city;

    @JsonProperty("neighborhood")
    private String neighborhood;

    @JsonProperty("street")
    private String street;

    @JsonProperty("street_number")
    private String streetNumber;

    @JsonProperty("complementary")
    private String complementary;

    @JsonProperty("zipcode")
    private String zipcode;

    public static AddressChangeClientRequest convertFrom(AddressChangeRequest addressChangeRequest) {
        var addressChangeClientRequest = new AddressChangeClientRequest();
        BeanUtils.copyProperties(addressChangeRequest, addressChangeClientRequest);
        addressChangeClientRequest.setCountry(COUNTRY_BRASIL);
        if (isEmpty(addressChangeClientRequest.getComplementary())) {
            addressChangeClientRequest.setComplementary(COMPLEMENT_COMPANY);
        }
        addressChangeClientRequest.setZipcode(tratarCep(addressChangeRequest.getZipcode()));
        return addressChangeClientRequest;
    }

    public static String tratarCep(String zipcode) {
        if (!isEmpty(zipcode)) {
            var zipCodeDefault = Pattern.compile(REGEX_JUST_NUMBERS).matcher(zipcode);
            var newZipcode = Strings.EMPTY;
            while (zipCodeDefault.find()) {
                newZipcode = newZipcode.concat(zipCodeDefault.group());
            }
            return newZipcode;
        }
        return Strings.EMPTY;
    }
}
