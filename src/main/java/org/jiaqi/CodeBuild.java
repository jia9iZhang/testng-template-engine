package org.jiaqi;

import lombok.SneakyThrows;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.jiaqi.config.CodeTempleConfiguration;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @Description 测试用例代码构建器
 * @Author jiaqi.zhang
 * @Date 2023/2/20-17:50
 */
public class CodeBuild {
    public static Properties properties;

    static {
        properties = new CodeTempleConfiguration().getProperties();
    }

    public static final String OPERATION = "operation";

    String authorName = properties.getProperty("authorName");

    String packageName = properties.getProperty("packageName");
    String savePath = properties.getProperty("savePath");
    String host = properties.getProperty("host");
    String className = properties.getProperty("className");
    String daoName = properties.getProperty("daoName");
    String insert = properties.getProperty("insert");
    String list = properties.getProperty("list");
    String update = properties.getProperty("update");
    String delete = properties.getProperty("delete");
    String testPath = properties.getProperty("testPath");
    String modelPath = properties.getProperty("modelPath");
    String modulePath = properties.getProperty("modulePath");
    String daoPath = properties.getProperty("daoPath");

    String bossUser = properties.getProperty("bossUser");

    /**
     * VelocityEngine初始化
     *
     * @return
     */
    public VelocityEngine intiVelocityEngine() {
        VelocityEngine velocityEngine = new VelocityEngine();
        //设置模版和输出的代码文件的编码方式
        Properties properties = new Properties();
        properties.setProperty(RuntimeConstants.ENCODING_DEFAULT, CharEncoding.UTF_8);
        properties.setProperty(RuntimeConstants.INPUT_ENCODING, CharEncoding.UTF_8);
        properties.setProperty(RuntimeConstants.OUTPUT_ENCODING, CharEncoding.UTF_8);
        velocityEngine.init(properties);
        return velocityEngine;
    }

    /**
     * 上下文数据初始化
     *
     * @return
     */
    public VelocityContext intiContext() {
        VelocityContext context = new VelocityContext();
        context.put("packageName", packageName);
        context.put("className", className);
        context.put("classNameLower", className.toLowerCase());
        context.put("authorName", authorName);
        context.put("daoName", daoName);
        context.put("host", host);
        context.put("bossUser", bossUser);
        return context;
    }

    @SneakyThrows
    public void build() {
        codeBuild(intiVelocityEngine(), intiContext(),makeDir()+"/");
    }

    /**
     * 生成对于.vm的代码文件
     *
     * @param velocityEngine
     * @param context
     * @param
     */
    public void codeBuild(VelocityEngine velocityEngine, VelocityContext context, String dirName) {
        System.out.println("dirName: "+dirName);
        //引入模版，通过模版路径"test.vm" ,将占位符数据和模版合并，输出代码文件
        Template testVm = velocityEngine.getTemplate(testPath);
        merge(testVm, context, dirName + className + "ApiTest.java");

        // 合并module文件
        Template moduleVm = velocityEngine.getTemplate(modulePath);
        merge(moduleVm, context, dirName + className + "Api.java");

        ArrayList<String> operationList = new ArrayList<>();
        operationList.add(insert);
        operationList.add(list);
        operationList.add(update);
        operationList.add(delete);

        for (String s : operationList) {
            if (s.equals(insert)) {
                context.put(OPERATION, StringUtils.capitalize(s));
                Template modelVm = velocityEngine.getTemplate(modelPath);
                System.out.println(dirName + s + className + "Model.java");
                merge(modelVm, context, dirName + s + className + "Model.java");
            }
            if (s.equals(list)) {
                context.put(OPERATION, StringUtils.capitalize(s));
                Template modelVm = velocityEngine.getTemplate(modelPath);
                merge(modelVm, context, dirName + s + className + "Model.java");
            }
            if (s.equals(update)) {
                context.put(OPERATION, StringUtils.capitalize(s));
                Template modelVm = velocityEngine.getTemplate(modelPath);
                merge(modelVm, context, dirName + s + className + "Model.java");
            }
            if (s.equals(delete)) {
                context.put(OPERATION, StringUtils.capitalize(s));
                Template modelVm = velocityEngine.getTemplate(modelPath);
                merge(modelVm, context, dirName + s + className + "Model.java");
            }
        }
        // 合并dao文件
        Template daoVm = velocityEngine.getTemplate(daoPath);
        merge(daoVm, context, dirName + daoName + ".java");
        System.out.println("成功");
    }

    /**
     * 合并数据和模版，输出文件
     *
     * @param template
     * @param ctx
     * @param path
     */
    private void merge(Template template, VelocityContext ctx, String path) {
        try (PrintWriter writer = new PrintWriter(path)) {
            //合并数据和模版，输出文件
            template.merge(ctx, writer);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件夹
     */
    public String makeDir(){
        File file = new File(savePath);
        String dirName = savePath + properties.getProperty("className").toLowerCase();
        if (file.length() == 0 || !new File(dirName).exists()) {
            if (new File(dirName).mkdir()) {
                return dirName;
            }
        }
        return dirName;
    }
}
