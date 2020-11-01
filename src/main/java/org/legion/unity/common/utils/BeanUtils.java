package org.legion.unity.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationUtils;
import org.legion.unity.common.base.BasePO;
import java.io.Serializable;
import java.lang.reflect.Field;

public class BeanUtils {

    public static void formatEmptyString(BasePO po) {
        if (po != null) {
            Class<?> type = po.getClass();
            Field[] fields = type.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType() == String.class) {
                    try {
                        String value = (String) Reflections.getValue(field, type, po);
                        if (StringUtils.isEmpty(value)) {
                            field.set(po, null);
                        }
                    } catch (Exception ignored) {

                    }
                }
            }
        }
    }

    public static<T extends Serializable> T deepClone(T entity) {
        if (entity != null) {
            return SerializationUtils.clone(entity);
        }
        return null;
    }

    public static <T> T deepClone(T entity, Class<T> type) {
        if (entity != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(objectMapper.writeValueAsString(entity), type);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
        return null;
    }

}
