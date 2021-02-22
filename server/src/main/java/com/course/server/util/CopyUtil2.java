package com.course.server.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CopyUtil2 {

    // 复制列表（多个对象）
    public static <T, E> List<T> copy(List<E> sourceList, Class<T> clazz) {
        List<T> targetList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sourceList)) {
            for (Object source : sourceList) {
                T target = copy(source, clazz);
                targetList.add(target);
            }
        }
        return targetList;
    }

    // 赋值单个对象
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) return null;
        T target;

        try {
            target = clazz.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException("无法copy对象：反射创建对象失败！");
        }
        return target;
    }
}
