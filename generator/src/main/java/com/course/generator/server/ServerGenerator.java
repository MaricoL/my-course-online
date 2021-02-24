package com.course.generator.server;

import com.course.generator.util.DBUtils;
import com.course.generator.util.Field;
import com.course.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerGenerator {
    // 系统路径分隔符
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    // 模块名
    private static final String MODULE = "business";

    //    static String toPath = "generator/src/main/java/com/course/generator/test/";

    // 将路径中非字母数字下划线的字符替换为当前系统的路径分隔符
    // 由于 FILE_SEPARATOR \ 为转义字符，所以需要加上 \ 进行转义，否则阿静报错
    static String toDtoPath = "server/src/main/java/com/course/server/dto/"
            .replaceAll("\\W", "\\" + FILE_SEPARATOR);
    static String toServicePath = "server/src/main/java/com/course/server/service/"
            .replaceAll("\\W", "\\" + FILE_SEPARATOR);
    static String toControllerPath = MODULE +"/src/main/java/com/course/business/controller/admin/"
            .replaceAll("\\W", "\\" + FILE_SEPARATOR);

    public static void main(String[] args) throws Exception {
        // 定义 service.ftl 模板中的模板参数
        String Domain = "Section";
        String domain = "section";
        String tableNameCn = "小节";
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableNameCn", tableNameCn);
        map.put("MODULE", MODULE);

        // 根据表名从数据库中获取该表所有字段
        List<Field> fieldList = DBUtils.getColumnByTableName(domain);
        map.put("fieldList", fieldList);
        // 从 fieldList 中收集所有field用到的JavaType，并去重
        Set<String> javaTypeSet = fieldList.stream().map(Field::getJavaType).collect(Collectors.toSet());
        map.put("javaTypeSet", javaTypeSet);


        // 生成 dto层 代码
        FreemarkerUtil.initFtl("dto.ftl");
        FreemarkerUtil.generator(toDtoPath + Domain + "Dto.java", map);

        // 生成 service层 代码
        FreemarkerUtil.initFtl("service.ftl");
        FreemarkerUtil.generator(toServicePath + Domain + "Service.java", map);

        // 生成 controller层 代码
        FreemarkerUtil.initFtl("controller.ftl");
        FreemarkerUtil.generator(toControllerPath + Domain + "Controller.java", map);
    }
}
