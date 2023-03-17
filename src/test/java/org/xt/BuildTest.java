package org.xt;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.*;

/**
 * @Description TODO
 * @Author jiaqi.zhang
 * @Date 2023/2/24-18:49
 */
public class BuildTest {

    @Test
    public void testMakeDir() {
        String s = new Build().makeDir();
        File file = new File(s);
        Assert.assertEquals(file.exists(),true,"创建文件夹失败");
    }
}