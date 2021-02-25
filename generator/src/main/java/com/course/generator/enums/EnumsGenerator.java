package com.course.generator.enums;

import java.io.File;

public class EnumsGenerator {
        private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String toEnumJsPath = "admin/public/static/js/Enum.js"
            .replaceAll("/", "\\" + FILE_SEPARATOR);

    public static void main(String[] args) {
        StringBuffer arrayJsStr = new StringBuffer();
        StringBuffer objectJsStr = new StringBuffer();

        // 专程 JSON 格式的字符串
//        toJsonString(SectionChargeEnum.class, arrayJsStr, objectJsStr);

        // 拼接字符串并添加换行符
        StringBuffer buffer = arrayJsStr.append(System.lineSeparator()).append(objectJsStr);

        // 写入 Enum.js 文件
//        writeJS(toEnumJsPath, buffer);
    }
}
