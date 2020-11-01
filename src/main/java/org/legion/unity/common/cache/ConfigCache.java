package org.legion.unity.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import org.legion.unity.admin.dao.ConfigDAO;
import org.legion.unity.admin.entity.Config;
import org.legion.unity.common.consts.AppConst;
import org.legion.unity.common.consts.SystemConst;
import org.legion.unity.common.ex.InitializationException;
import org.legion.unity.common.utils.SpringUtils;
import org.legion.unity.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class ConfigCache implements ICache<String, String> {

    private static final Cache<String, Config> needRestartConfigCache;
    public static final String KEY = ConfigCache.class.getName();
    private static final Map<String, Properties> propertyList = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(ConfigCache.class);

    static {
        needRestartConfigCache = CacheBuilder.newBuilder().build();
        ConfigDAO configDAO = SpringUtils.getBean(ConfigDAO.class);
        List<Config> configList = Lists.newArrayList(configDAO.findAll());
        for (Config config : configList) {
            if (StringUtils.parseBoolean(config.getIsNeedRestart())) {
                needRestartConfigCache.put(config.getConfigKey(), config);
                log.info("Config Cached -> " + config.getConfigKey() + " : " + config.getConfigValue());
            }
        }
        Properties unity = new Properties();
        ConfigCache configCache = new ConfigCache();
        try (FileInputStream inputStream = new FileInputStream(new File(SystemConst.CLASSPATH
                + "config", "unity.properties"))) {
            unity.load(inputStream);
            propertyList.put("unity", unity);
        } catch (Exception e) {
            log.error("", e);
            throw new InitializationException("Properties NOT Found: unity.properties");
        }
        if (AppConst.MODE_DEV.equalsIgnoreCase(unity.getProperty("server.mode"))) {
            loadProperties(AppConst.MODE_DEV);
        } else if (AppConst.MODE_UAT.equalsIgnoreCase(unity.getProperty("server.mode"))) {
            loadProperties(AppConst.MODE_UAT);
        }else if (AppConst.MODE_PRD.equalsIgnoreCase(unity.getProperty("server.mode"))) {
            loadProperties(AppConst.MODE_PRD);
        }
        CachePool.setCache(KEY, configCache);
    }

    @Override
    public String get(String key) {
        String value = null;
        Set<String> keySet = propertyList.keySet();
        for (String k : keySet) {
            Properties properties = propertyList.get(k);
            value = properties.getProperty(key);
            if (StringUtils.isNotEmpty(value)) {
                break;
            }
        }
        if (StringUtils.isEmpty(value)) {
            Config config = needRestartConfigCache.getIfPresent(key);
            if (config != null) {
                value = config.getConfigValue();
            }
        }
        if (StringUtils.isEmpty(value)) {
            ConfigDAO configDAO = SpringUtils.getBean(ConfigDAO.class);
            Config config = configDAO.findByConfigKey(key);
            if (config != null) {
                value = config.getConfigValue();
            }
        }
        if (StringUtils.isEmpty(value)) {
            log.warn("Properties Key NOT Found: " + key);
        }
        return value;
    }

    @Override
    public void set(String key, String value) {

    }

    private static void loadProperties(String mode) {
        try (FileInputStream inputStream = new FileInputStream(new File(SystemConst.CLASSPATH
                + "config", mode.toLowerCase() + ".properties"))) {
            Properties env = new Properties();
            env.load(inputStream);
            propertyList.put(mode, env);
            log.info("Properties Cached: " + mode.toLowerCase() + ".properties");
        } catch (Exception e) {
            log.error("", e);
            throw new InitializationException("properties NOT Found: " + mode.toLowerCase() + ".properties");
        }
    }
}
