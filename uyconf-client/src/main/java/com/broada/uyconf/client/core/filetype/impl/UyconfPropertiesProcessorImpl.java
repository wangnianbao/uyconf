package com.broada.uyconf.client.core.filetype.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.broada.uyconf.client.core.filetype.UyconfFileTypeProcessor;
import com.broada.uyconf.client.support.utils.ConfigLoaderUtils;

/**
 * Properties 处理器
 *
 * @author wnb
 */
public class UyconfPropertiesProcessorImpl implements UyconfFileTypeProcessor {

    @Override
    public Map<String, Object> getKvMap(String fileName) throws Exception {

        Properties properties;

        // 读取配置
        properties = ConfigLoaderUtils.loadConfig(fileName);
        if (properties == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        for (Object object : properties.keySet()) {

            String key = String.valueOf(object);
            Object value = properties.get(object);

            map.put(key, value);
        }

        return map;
    }
}
