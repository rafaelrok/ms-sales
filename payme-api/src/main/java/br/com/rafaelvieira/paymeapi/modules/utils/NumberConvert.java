package br.com.rafaelvieira.paymeapi.modules.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author rafae
 */

@Slf4j
public class NumberConvert {

    private static final Integer ONE_DECIMAL_PLACE = 1;
    private static final Integer TWO_DECIMAL_PLACES = 2;
    private static final String DECIMAL_SEPARATOR = ".";
    private static final String PREFIX_TWO_DECIMAL_PLACES = ".00";
    private static final Integer INITIAL_POSITION = 0;

    public static BigDecimal convertTwoDecimalPlaces(Double number){
        return BigDecimal
                .valueOf(number)
                .setScale(TWO_DECIMAL_PLACES, RoundingMode.HALF_UP);
    }

    public static BigDecimal handlePaymentAmount(BigDecimal paymentAmount){
        try{
            var paymentAmountString = handleInitialNumericString(paymentAmount.toString());
            var sizeNumber = paymentAmountString.length();
            var sizeInteger = sizeNumber - TWO_DECIMAL_PLACES;
            var decimalValue = paymentAmountString.substring(sizeInteger);
            var innerValue = paymentAmountString.substring(INITIAL_POSITION, sizeInteger);
            var fullNumberTwoDecimalPlaces = innerValue.concat(DECIMAL_SEPARATOR).concat(decimalValue);
            return convertTwoDecimalPlaces(Double.parseDouble(fullNumberTwoDecimalPlaces));
        } catch (Exception e){
            log.error("Error trying to convert the number to two places within the number.");
            return BigDecimal.ZERO;
        }
    }

    private static String handleInitialNumericString(String amount) {
        if (amount.contains(PREFIX_TWO_DECIMAL_PLACES)) {
            amount = amount.replace(PREFIX_TWO_DECIMAL_PLACES, Strings.EMPTY);
        }
        if (amount.length() == ONE_DECIMAL_PLACE) {
            amount = amount.concat(String.valueOf(INITIAL_POSITION));
        }
        return amount;
    }
}
