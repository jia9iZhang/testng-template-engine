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
public class CodeBuilder {

    private final Properties properties;

    public CodeBuilder(Properties properties) {
        this.properties = properties;
    }

    /**
     * 上下文数据初始化
     *
     * @return
     */
    private Context initContext() {
        Context context = new Context();
        context.put("packageName", properties.getProperty("packageName"));
        context.put("className", StringUtils.capitalize(properties.getProperty("className")));
        context.put("classNameLower", properties.getProperty("className").toLowerCase());
        context.put("authorName", properties.getProperty("authorName"));
        context.put("daoName", StringUtils.capitalize(properties.getProperty("daoName")));
        context.put("host", properties.getProperty("host"));
        context.put("bossUser", properties.getProperty("bossUser"));
        return context;
    }

    /**
     * 合并数据和模板，输出文件
     *
     * @param template
     * @param ctx
     * @param path
     */
    private void merge(Template template, Context ctx, String path) throws Exception {
        try (PrintWriter writer = new PrintWriter(path)) {
            template.merge(ctx, writer);
            writer.flush();
        }
    }

    /**
     * 构建测试用例代码
     * @throws Exception 
     */
    public void build() throws Exception {
    	
    	// 初始化 Velocity 引擎
    	VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.ENCODING_DEFAULT, CharEncoding.UTF_8);
        engine.setProperty(RuntimeConstants.INPUT_ENCODING, CharEncoding.UTF_8);
        engine.setProperty(RuntimeConstants.OUTPUT_ENCODING, CharEncoding.UTF_8);
        
        // 初始化 Velocity 上下文
    	Context context = initContext();
    	
    	// 合并模板生成测试文件
        String testFile = properties.getProperty("savePath") + "/" + context.get("className") + "ApiTest.java";
        Template testVm = engine.getTemplate(properties.getProperty("testPath"));
        merge(testVm, context, testFile);

        // 合并模板生成模块文件
        String moduleFile = properties.getProperty("savePath") + "/" + context.get("className") + "Api.java";
        Template moduleVm = engine.getTemplate(properties.getProperty("modulePath"));
        merge(moduleVm, context, moduleFile);

        // 合并模板生成操作项对应的model文件
        List<String> operationList = Arrays.asList("insert", "list", "update", "delete");
        String modelPath = properties.getProperty("savePath") + "/" + StringUtils.capitalize(properties.getProperty("className"));
        Template modelVm = engine.getTemplate(properties.getProperty("modelPath"));

        for(String s : operationList) {
            String modelFile = modelPath + StringUtils.capitalize(s) + "Model.java";
            Context modelCtx = new Context(context);
            modelCtx.put("operation", StringUtils.capitalize(s));
            merge(modelVm, modelCtx, modelFile);
        }

        // 合并模板生成dao文件
        String daoFile = properties.getProperty("savePath") + "/" + StringUtils.capitalize(properties.getProperty("daoName")) + ".java";
        Template daoVm = engine.getTemplate(properties.getProperty("daoPath"));
        merge(daoVm, context, daoFile);
    }
}

