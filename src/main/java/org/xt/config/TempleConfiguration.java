package org.xt.config;

import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Description 读取类路径中的配置文件
 * @Author jiaqi.zhang
 * @Date 2023/2/16-11:33
 */
public class TempleConfiguration {
    @SneakyThrows
    public Properties getProperties() {
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = TempleConfiguration.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        // 使用properties对象加载输入流
        properties.load(in);
        return properties;
    }
}
