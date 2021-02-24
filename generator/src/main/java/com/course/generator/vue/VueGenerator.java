package com.course.generator.vue;

import com.course.generator.util.DBUtils;
import com.course.generator.util.Field;
import com.course.generator.util.FreemarkerUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class VueGenerator {
    // 系统路径分隔符
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    // 模块名
    private static final String MODULE = "business";

    // 将路径中非字母数字下划线的字符替换为当前系统的路径分隔符
    // 由于 FILE_SEPARATOR \ 为转义字符，所以需要加上 \ 进行转义，否则阿静报错

    static String toVuePath = "admin/src/views/admin/"
            .replaceAll("/", "\\" + FILE_SEPARATOR);
    static String generatorConfigPath = "server/src/main/resources/generator/generatorConfig.xml"
            .replaceAll("/", "\\" + FILE_SEPARATOR);


    public static void main(String[] args) throws Exception {
        // 只生成配置文件中的第一个table节点
        File file = new File(generatorConfigPath);
        SAXReader reader = new SAXReader();
        //读取xml文件到Document中
        Document doc = reader.read(file);
        //获取xml文件的根节点
        Element rootElement = doc.getRootElement();
        //读取context节点
        Element contextElement = rootElement.element("context");
        //定义一个Element用于遍历
        Element tableElement;
        //取第一个“table”的节点
        tableElement = contextElement.elementIterator("table").next();
        String Domain = tableElement.attributeValue("domainObjectName");
        String tableName = tableElement.attributeValue("tableName");
        String tableNameCn = DBUtils.getTableComment(tableName);
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        System.out.println("表：" + tableElement.attributeValue("tableName"));
        System.out.println("Domain：" + tableElement.attributeValue("domainObjectName"));

        // 定义 service.ftl 模板中的模板参数
//        String Domain = "Section";
//        String domain = "section";
//        String tableNameCn = "小节";
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("tableNameCn", tableNameCn);
        map.put("MODULE", MODULE);

        // 根据表名从数据库中获取该表所有字段
        List<Field> fieldList = DBUtils.getColumnByTableName(tableName);
        map.put("fieldList", fieldList);
        // 从 fieldList 中收集所有field用到的JavaType，并去重
        Set<String> javaTypeSet = fieldList.stream().map(Field::getJavaType).collect(Collectors.toSet());
        map.put("javaTypeSet", javaTypeSet);


        // 生成 vue 代码
        FreemarkerUtil.initFtl("vue.ftl");
        FreemarkerUtil.generator(toVuePath + domain + ".vue", map);
    }
}
