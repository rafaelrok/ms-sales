package br.com.rafaelvieira.paymeapi.modules.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rafae
 */

@Slf4j
public class JsonUtil {
    private static final String EMPTY_JSON_OBJECT = "{}";

    public static String convertObjectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error converting object to json", e);
            return EMPTY_JSON_OBJECT;
        }
    }
}
