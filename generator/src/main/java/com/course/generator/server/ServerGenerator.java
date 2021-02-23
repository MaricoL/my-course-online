package com.course.generator.server;

import com.course.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerGenerator {
//    static String toPath = "generator/src/main/java/com/course/generator/test/";
    static String toServicePath = "server/src/main/java/com/course/server/service/";


    public static void main(String[] args) throws IOException, TemplateException {
        // 定义 service.ftl 模板中的模板参数
        String Domain = "Section";
        String domain = "section";
        Map<String, String> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);

        FreemarkerUtil.initFtl("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java",map);
    }
}
