package org.xt;

import jdk.nashorn.internal.ir.Flags;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.io.PrintWriter;

/**
 * @Description TODO
 * @Author jiaqi.zhang
 * @Date 2023/2/24-18:22
 */
public class FileTest {
    @Test
    public void merge() {
        String path = "output/";
        String name = "output/";
        boolean flag = true;
        File file = new File(path);
        for (File listFile : file.listFiles()) {
            if (listFile.isDirectory()&&!listFile.getName().equals("AioutboundcallFlowDefinition")){
                flag = false;
            }
        }

        boolean aioutboundcallFlowDefinition = new File(path+"AioutboundcallFlowDefinition").mkdir();
        //try (PrintWriter writer = new PrintWriter(path)) {
        //    //合并数据和模版，输出文件
        //    template.merge(ctx, writer);
        //    writer.flush();
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }
}
