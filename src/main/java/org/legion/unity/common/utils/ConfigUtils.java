package org.legion.unity.common.utils;

import org.legion.unity.common.cache.CachePool;
import org.legion.unity.common.cache.ConfigCache;

public class ConfigUtils {

    private static ConfigCache configCache = CachePool.getCache(ConfigCache.KEY, ConfigCache.class);

    public static String get(String key) {
        return configCache.get(key);
    }
}
