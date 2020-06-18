package com.learn.base.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bingo on 2020/6/18 0018.
 * 对象比较器
 */

public class ObjectComparer {
    /**
     * 获取对象的全部属性字段
     * @param object
     * @return
     */
    private static Field[] getAllFields(Object object) {
        Class clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 判断两个对象的属性值是否一致
     * @param obj 对象1
     * @param obj2 对象2
     * @return
     */
    private boolean compare(Object obj, Object obj2) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Field[] fields = getAllFields(obj);
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                // 字段值
                if (!fields[j].get(obj).equals(fields[j].get(obj2))) {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }
}
