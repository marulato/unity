package org.legion.unity.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.Collection;
import java.util.List;

public final class Redis {

    private static final StringRedisTemplate srt = SpringUtils.getBean(StringRedisTemplate.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void put(String key, String value) {
        if (StringUtils.isNotBlank(key)) {
            srt.opsForValue().set(key, value);
        }
    }

    public static void putAsJson(String key, Object value) throws Exception {
        if (StringUtils.isNotBlank(key)) {
            if (value instanceof String) {
                srt.opsForValue().set(key, (String) value);
            } else if (value != null) {
                srt.opsForValue().set(key, objectMapper.writeValueAsString(value));
            }
        }
    }

    public static void rightPushEach(String key, Collection<String> list) {
        if (StringUtils.isNotBlank(key) && list != null && !list.isEmpty()) {
            srt.opsForList().rightPushAll(key, list.toArray(new String[0]));
        }
    }

    public static void rightPushEach(String key, String... list) {
        if (StringUtils.isNotBlank(key) && list != null) {
            srt.opsForList().rightPushAll(key, list);
        }
    }

    public static void leftPushEach(String key, Collection<String> list) {
        if (StringUtils.isNotBlank(key) && list != null && !list.isEmpty()) {
            srt.opsForList().leftPushAll(key, list.toArray(new String[0]));
        }
    }

    public static void leftPushEach(String key, String... list) {
        if (StringUtils.isNotBlank(key) && list != null) {
            srt.opsForList().leftPushAll(key, list);
        }
    }

    public static void delete(String key) {
        srt.delete(key);
    }

    public static String get(String key) {
        return srt.opsForValue().get(key);
    }

    public static <T> T getJsonObject(String key, Class<T> type) throws Exception {
        String json = srt.opsForValue().get(key);
        if (StringUtils.isNotBlank(json)) {
            return objectMapper.readValue(json, type);
        }
        return null;
    }
}
