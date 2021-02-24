package com.course.generator.server;

import com.course.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerGenerator {
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    //    static String toPath = "generator/src/main/java/com/course/generator/test/";

    // 将路径中非字母数字下划线的字符替换为当前系统的路径分隔符
    // 由于 FILE_SEPARATOR \ 为转义字符，所以需要加上 \ 进行转义，否则阿静报错
    static String toServicePath = "server/src/main/java/com/course/server/service/"
            .replaceAll("\\W", "\\" + FILE_SEPARATOR);
    static String toControllerPath = "business/src/main/java/com/course/business/controller/admin/"
            .replaceAll("\\W", "\\" + FILE_SEPARATOR);


    public static void main(String[] args) throws IOException, TemplateException {
        // 定义 service.ftl 模板中的模板参数
        String Domain = "Section";
        String domain = "section";
        Map<String, String> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);

        // 生成 service层 代码
        FreemarkerUtil.initFtl("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);

        // 生成 controller层 代码
        FreemarkerUtil.initFtl("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + Domain + "Controller.java", map);
    }
}
