package ploton.Brokers_Kafka_Practice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;

@Slf4j
public class EntityUtils {
    public static void updatedEntity(Object entity, Map<String, Object> updates) {
        Class clazz = entity.getClass();

        for (Map.Entry<String, Object> updateField : updates.entrySet()) {
            String updateFieldName = updateField.getKey();
            Object updateFieldValue = updateField.getValue();

            try {
                Field field = clazz.getDeclaredField(updateFieldName);
                field.setAccessible(true);
                field.set(entity, updateFieldValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.warn("UpdateFieldsException: " + e.getMessage());
                throw new IllegalArgumentException("UpdateFieldsException. OrderEntity - Field -" + updateFieldName
                        + "\n" + e.getMessage());
            }
        }
    }

    public static String convertToJson(Object entity) {
        try {
            return new ObjectMapper().writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException: " + e.getMessage());
            return null;
        }
    }

    public static Object convertFromJson(String entityJson, Class entityClass) {
        try {
            return new ObjectMapper().readValue(entityJson, entityClass);
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException: " + e.getMessage());
            return null;
        }
    }
}
