package com.course.generator.enums;

import com.course.server.enums.CourseChargeEnum;
import com.course.server.enums.CourseLevelEnum;
import com.course.server.enums.CourseStatusEnum;
import com.course.server.enums.SectionChargeEnum;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnumsGenerator {
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String toEnumJsPath = "admin/public/static/js/Enum.js"
            .replaceAll("/", "\\" + FILE_SEPARATOR);

    public static void main(String[] args) {
        StringBuffer arrayJsStr = new StringBuffer();
        StringBuffer objectJsStr = new StringBuffer();


        try {
            // 转成 JSON 格式的字符串
            toJsonString(SectionChargeEnum.class, arrayJsStr, objectJsStr);
            toJsonString(CourseLevelEnum.class, arrayJsStr, objectJsStr);
            toJsonString(CourseChargeEnum.class, arrayJsStr, objectJsStr);
            toJsonString(CourseStatusEnum.class, arrayJsStr, objectJsStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 拼接字符串并添加换行符
        StringBuffer buffer = arrayJsStr.append(System.lineSeparator()).append(objectJsStr);

        // 写入 Enum.js 文件
        writeJS(toEnumJsPath, buffer);
    }

    private static void writeJS(String toEnumJsPath, StringBuffer buffer) {
        try {
            buffer = new StringBuffer("export default {").append(System.lineSeparator())
                    .append(buffer)
                    .append("}");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(toEnumJsPath)));
            writer.write(buffer.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void toJsonString(Class<?> clazz, StringBuffer arrayJsStr, StringBuffer objectJsStr) throws Exception {
        String key = toUnderline(clazz.getSimpleName());
        toJsonString(clazz, key, arrayJsStr, objectJsStr);
    }

    private static void toJsonString(Class<?> clazz, String key, StringBuffer arrayJsStr, StringBuffer objectJsStr) throws Exception {
        Object[] enumConstants = clazz.getEnumConstants();
        Method name = clazz.getMethod("name");
        Method getCode = clazz.getMethod("getCode");
        Method getDesc = clazz.getMethod("getDesc");

        // 生成数组
        arrayJsStr.append(key).append("_ARRAY")
                .append(":[");
        for (Object enumConstant : enumConstants) {
            arrayJsStr
                    .append("{key:\"")
                    .append(getCode.invoke(enumConstant))
                    .append("\",value:\"")
                    .append(getDesc.invoke(enumConstant))
                    .append("\"},");
        }
        // 去除末尾的逗号
        arrayJsStr.deleteCharAt(arrayJsStr.length() - 1);
        arrayJsStr.append("],");
        arrayJsStr.append(System.lineSeparator());

        // 生成对象
        objectJsStr.append(key).append(":{");
        for (Object enumConstant : enumConstants) {
            objectJsStr
                    .append(name.invoke(enumConstant))
                    .append(":{key:\"")
                    .append(getCode.invoke(enumConstant))
                    .append("\",value:\"")
                    .append(getDesc.invoke(enumConstant))
                    .append("\"},");
        }
        objectJsStr.deleteCharAt(objectJsStr.length() - 1);
        objectJsStr.append("},");
        objectJsStr.append(System.lineSeparator());

    }

    private static String toUnderline(String className) {
        String result = underline(className);
        // 将 _section_charge_enum 转换成 SECTION_CHARGE
        return result.substring(1).replace("_enum", "").toUpperCase();
    }

    /**
     * 将类名转换成大写下划线格式
     * 如：SectionChargeEnum 转换成 _section_charge_enum
     */
    private static String underline(String className) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(className);
        StringBuffer resultBuffer = new StringBuffer(className);
        if (matcher.find()) {
            resultBuffer = new StringBuffer();
            matcher.appendReplacement(resultBuffer, "_" + matcher.group().toLowerCase());
            matcher.appendTail(resultBuffer);
        } else {
            return resultBuffer.toString();
        }
        return underline(resultBuffer.toString());
    }
}
