package org.jiaqi.config;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Description 读取模版配置文件
 * @Author jiaqi.zhang
 * @Date 2023/2/16-11:33
 */
public class CodeTempleConfiguration {
    @SneakyThrows
    public Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream in = CodeTempleConfiguration.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (in != null) {
                properties.load(in);
            } else {
                throw new FileNotFoundException("config.properties not found");
            }
        }
        return properties;
    }
}

