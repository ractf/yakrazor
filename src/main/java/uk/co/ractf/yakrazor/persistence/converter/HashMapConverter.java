package uk.co.ractf.yakrazor.persistence.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private static final Logger logger = LoggerFactory.getLogger(HashMapConverter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(final Map<String, Object> data) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(data);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return json;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String json) {
        Map<String, Object> data = null;
        try {
            data = objectMapper.readValue(json, Map.class);
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }

        return data;
    }

}