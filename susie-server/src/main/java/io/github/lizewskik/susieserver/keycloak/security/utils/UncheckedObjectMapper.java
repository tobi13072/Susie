package io.github.lizewskik.susieserver.keycloak.security.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class UncheckedObjectMapper extends ObjectMapper {
    public Map<String, String> map(String body) {
        try {
            return this.readValue(body, new TypeReference<>(){});
        } catch (JsonProcessingException exception) {
            log.error(exception.getMessage());
        }
        return null;
    }
}
