package com.zy.manage.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理节点工具类
 *
 * @author huangzhongcheng
 */
public class NodeUtils {


    /**
     * 处理节点层级关系方法
     *
     * @param objList      需要处理的集合
     * @param parentField  父节点字段
     * @param idField      本身节点字段
     * @param flag         是否需要排序
     * @param storeField   排序字段
     * @param sonListField 存子节点的字段
     * @return
     */
    public static List sortList(List<Object> objList, String parentField, String idField, boolean flag, String storeField, String sonListField) {
        List<Object> newList = objList.stream().collect(Collectors.toList());
        objList.stream().forEach(root -> {
            List<Object> sonList = null;
            if (flag)
                sonList = sort(newList.stream().filter(o -> isSon(root, o, parentField, idField)).collect(Collectors.toList()), storeField);
            else
                sonList = newList.stream().filter(o -> isSon(root, o, parentField, idField)).collect(Collectors.toList());
            try {
                Field field = root.getClass().getDeclaredField(sonListField);
                field.setAccessible(true);
                field.set(root, sonList);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            newList.removeAll(sonList);
        });
        if (flag)
            return sort(newList,storeField);
        return newList;
    }


    public static boolean isSon(Object parentObj, Object sonObject, String parentField, String idField) {
        try {
            Field pField = parentObj.getClass().getDeclaredField(idField);
            pField.setAccessible(true);
            Field sField = sonObject.getClass().getDeclaredField(parentField);
            sField.setAccessible(true);
            if (pField.get(parentObj) != null && sField.get(sonObject) != null && pField.get(parentObj) instanceof Long && sField.get(sonObject) instanceof Long) {
                Long parentId = (Long) pField.get(parentObj);
                Long sonParentId = (Long) sField.get(sonObject);
                if (parentId.equals(sonParentId))
                    return true;
                return false;
            }
            return false;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static List<Object> sort(List<Object> objList, String storeField) {
        return objList.stream().sorted((Object o1, Object o2) -> {
            try {
                Field field = o1.getClass().getDeclaredField(storeField);
                field.setAccessible(true);
                if (field.get(o1) != null && field.get(o1) instanceof Integer) {
                    Integer o1Store = field.get(o1) == null ? 0 : (Integer) field.get(o1);
                    Integer o2Store = field.get(o2) == null ? 0 : (Integer) field.get(o2);
                    return o1Store == o2Store ? 0 : o1Store < o2Store ? o1Store : o2Store;
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return 0;
        }).collect(Collectors.toList());
    }
}
