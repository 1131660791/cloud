package com.util.common.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * properties 工具类
 *
 * @author hzw
 */
public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    private final Properties properties;

    public PropertiesUtil(String... resourcesPaths) {
        properties = loadProperties(resourcesPaths);
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * 载入多个文件, 文件路径使用Spring Resource格式.
     */
    private Properties loadProperties(String... resourcesPaths) {
        Properties props = new Properties();

        for (String location : resourcesPaths) {
            InputStream is = null;
            try {
                Resource resource = resourceLoader.getResource(location);
                is = resource.getInputStream();
                props.load(is);
            } catch (IOException ex) {
                logger.info("Could not load properties from path:" + location + ", " + ex.getMessage());
            } finally {
                try {
                    is.close();
                } catch (Exception e) {

                }
            }
        }
        return props;
    }


    /**
     * 取出Property，但以System的Property优先,取不到返回空字符串.
     */
    private String getValue(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        return "";
    }

    /**
     * 取出String类型的Property，但以System的Property优先,如果都为Null则抛出异常.
     */
    public String getProperty(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
    }

    /**
     * 取出String类型的Property，但以System的Property优先.如果都为Null则返回Default值.
     */
    public String getProperty(String key, String defaultValue) {
        String value = getValue(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     */
    public Integer getInteger(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Integer.valueOf(value);
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     */
    public Integer getInteger(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Integer.valueOf(value) : defaultValue;
    }

    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     */
    public Double getDouble(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Double.valueOf(value);
    }

    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     */
    public Double getDouble(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Double.valueOf(value) : defaultValue;
    }

    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null抛出异常,如果内容不是true/false则返回false.
     */
    public Boolean getBoolean(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Boolean.valueOf(value);
    }

    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null则返回Default值,如果内容不为true/false则返回false.
     */
    public Boolean getBoolean(String key, boolean defaultValue) {
        String value = getValue(key);
        return value != null ? Boolean.valueOf(value) : defaultValue;
    }


    /**
     * 随意获取 properties key 相关值
     *
     * @param par          - 配置文件定义的 key
     * @param propertiesName - 配置文件名称
     * @return
     * @throws IOException
     */
    public String getProperties(String par, String propertiesName) throws IOException {
        Properties pps = new Properties();
        InputStream is = this.getClass().getResourceAsStream(propertiesName);
        pps.load(is);
        String returnStr = "";
        /**
         * 得到配置文件的名字
         */
        @SuppressWarnings("rawtypes")
        Enumeration enum1 = pps.propertyNames();
        while (enum1.hasMoreElements()) {
            String strKey = (String) enum1.nextElement();
            if (strKey.equals(par)) {
                returnStr = pps.getProperty(strKey);
            }
        }
        return returnStr;
    }

    /**
     *
     */

    public static void main(String[] args) throws IOException {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        // 获取propertes 文件  key 对应的值
        String value = propertiesUtil.getProperties("CascadeApproval.Ip", "/application.properties");
    }

}
