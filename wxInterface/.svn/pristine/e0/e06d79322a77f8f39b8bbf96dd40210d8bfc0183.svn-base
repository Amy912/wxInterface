package com.emagsoftware.frame.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * @Title: 定义读取Properties文件类
 * @Description:
 * @Copyright: Copyright (c) 2012-7-30
 * @Company: 幻方朗睿
 * @Author: Andy.D
 * @Version: 1.0
 */

public class MyPropertiesConfig {

    public HashMap<String, String> loadValues(InputStream inputStream) throws Exception {
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (Exception ex) {
            throw ex;
        } finally {
            inputStream.close();
        }
        HashMap<String, String> result = new HashMap<String, String>();
        Object[] keys = properties.keySet()
                .toArray();
        for (int i = 0; i < keys.length; i++) {
            String key = (String) keys[i];
            String value = properties.getProperty(key);
            if (value != null)
                value = new String(value.getBytes("ISO8859-1"));
            result.put(key.toLowerCase(),
                    value);
        }
        return result;
    }

}
