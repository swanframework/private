package org.zongf.utils.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * @Description: 验证工具类
 * @since 1.0
 * @author: zongf
 * @date: 2019-06-12 10:39
 */
public class ValidateUtil {

    /**
     * @Description: 判断对象是否为null
     * @param object 任意类型对象
     * @return: true/false
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:42:05
     */
    public static boolean isNull(Object object) {
        return object == null ? true : false;
    }

    /**
     * @Description: 判断字符串是否为空. null和纯空格均视为空
     * @param str 字符串
     * @return: true/false
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:43:48
     */
    public static boolean isEmpty(String str) {
        if(str == null || "".equals(str.trim())){
            return true;
        }
        return false;
    }

    /**
     * @Description: 判断集合是否为空. null和size为0均视为空
     * @param collection 集合或其子类型, 如:collection, list, set等
     * @return: true/false
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:45:42
     */
    public static boolean isEmpty(Collection collection) {
        if (collection == null || collection.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * @Description: 判断map是否为空. null和size为0均视为空
     * @param map map 类型
     * @return: true/false
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:45:42
     */
    public static boolean isEmpty(Map map) {
        if (map == null || map.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * @Description: 判断数组是否为空. null和length为0均视为空
     * @param array 任意类型数组
     * @return: true/false
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:45:42
     */
    public static boolean isEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    //****************************************** 反义校验 **********************************************************

    /**
     * @Description: 判断对象是否非null
     * @param object 任意类型对象
     * @return: true/false
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:42:05
     */
    public static boolean isNotNull(Object object) {
        return !isNotNull(object);
    }

    /**
     * @Description: 判断字符串是否非空. null和纯空格均视为空
     * @param str 字符串
     * @return: true/false
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:43:48
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * @Description: 判断集合是否非空. null和size为0均视为空
     * @param collection 集合或其子类型, 如:collection, list, set等
     * @return: true/false
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:45:42
     */
    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    /**
     * @Description: 判断map是否非空. null和size为0均视为空
     * @param map map 类型
     * @return: true/false
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:45:42
     */
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    /**
     * @Description: 判断数组是否非空. null和length为0均视为空
     * @param array 任意类型数组
     * @return: true/false
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:45:42
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }
}
