package org.xt.config;

import lombok.SneakyThrows;
import org.testng.annotations.Test;
import org.xt.config.TempleConfiguration;

import java.io.IOException;
import java.util.Properties;

/**
 * @Description TODO
 * @Author jiaqi.zhang
 * @Date 2023/2/16-11:44
 */
public class TempleConfigurationTest {

    @Test
    @SneakyThrows
    public void testGetConfig() {
        TempleConfiguration templeConfiguration = new TempleConfiguration();
        Properties properties = templeConfiguration.getProperties();
        //获取key对应的value值
        System.out.println(properties.getProperty("savePath"));
    }
}