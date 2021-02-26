package com.course.generator.util;

import com.course.generator.enums.EnumsGenerator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class DBUtils {
    private static final String url;
    private static final String username;
    private static final String password;
    private static final String driverClass;

    static {
        url = "jdbc:mysql://localhost:3306/courseimooc?characterEncoding=UTF8&autoReconnect=true";
        username = "courseimooc";
        password = "courseimooc";
        driverClass = "com.mysql.jdbc.Driver";
    }

    private static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    // 获取数据库表注释
    public static String getTableComment(String tableName) throws Exception {
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        String sql = "select table_comment from information_schema.tables Where table_name = '" + tableName + "'";
        ResultSet rs = stmt.executeQuery(sql);
        String tableNameCn = null;
        while (rs.next()) {
            tableNameCn = rs.getString("table_comment");
        }
        rs.close();
        stmt.close();
        conn.close();
        System.out.println("表名：" + tableNameCn);
        return tableNameCn;
    }

    public static List<Field> getColumnByTableName(String tableName) throws Exception {
        List<Field> fieldList = new ArrayList<>();
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        String sql = "show full columns from courseimooc." + tableName;
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String columnName = rs.getString("Field");
            String type = rs.getString("Type");
            String comment = rs.getString("Comment");
            String nullable = rs.getString("Null");
            Field field = new Field();
            field.setName(columnName);
            field.setNameHump(lineToHump(columnName));
            field.setNameBigHump(lineToBigHump(columnName));
            field.setNameCn(extractNameCn(comment));
            field.setType(type);
            field.setJavaType(sqlTypeToJavaType(type));
            field.setComment(comment);
            field.setNullable("YES".equals(nullable));
            field.setLength(extractLength(type));
            field.setEnums(isEnums(comment));
            field.setEnumsConst(extractEnumsConst(comment));
            fieldList.add(field);
        }
        rs.close();
        stmt.close();
        conn.close();
        System.out.println(tableName + "表的列信息：" + fieldList);
        return fieldList;
    }

    // 从 comment注释 中截取枚举字段名
    private static String extractEnumsConst(String comment) {
        if (isEnums(comment)) {
            String enumName = comment.substring(comment.indexOf("[") + 1, comment.indexOf("]"));
            return EnumsGenerator.toUnderline(enumName);
        } else {
            return null;
        }
    }

    // 判断该字段是否有对应的枚举字段
    private static Boolean isEnums(String comment) {
        return comment.contains("枚举");
    }

    // 从 type 类型中提取长度
    // 如：char(8)
    private static Integer extractLength(String type) {
        return type.toUpperCase().contains("VARCHAR") ?
                Integer.parseInt(type.substring(type.indexOf("(") + 1, type.length() - 1)) :
                0;
    }

    // 将列名中的下划线转换成小驼峰
    private static String lineToHump(String columnName) {
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("_(\\w)");
        Matcher matcher = pattern.matcher(columnName);
        while (matcher.find()) {
            // matcher.group(0)：返回整个字符串
            // matcher.group(1)：返回第一个括号里的字符串
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    // 将列名中的下划线转换成大驼峰
    private static String lineToBigHump(String columnName) {
        String s = lineToHump(columnName);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    // 从 comment 中提取 字段中文名
    // 如：课程|course.id，返回：课程
    private static String extractNameCn(String comment) {
        return comment.contains("|") ? comment.substring(0, comment.indexOf('|')) : comment;
    }

    // 将sql类型转换为java类型
    private static String sqlTypeToJavaType(String type) {
        String typeUpperCase = type.toUpperCase();
        if (Stream.of("VARCHAR", "CHAR", "TEXT").anyMatch(typeUpperCase::contains)) {
            return "String";
        } else if (Stream.of("DATETIME").anyMatch(typeUpperCase::contains)) {
            return "Date";
        } else if (Stream.of("INT").anyMatch(typeUpperCase::contains)) {
            return "Integer";
        } else if (Stream.of("LONG").anyMatch(typeUpperCase::contains)) {
            return "Long";
        } else if (Stream.of("DECIMAL").anyMatch(typeUpperCase::contains)) {
            return "BigDecimal";
        } else {
            return "String";
        }
    }
}
