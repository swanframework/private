package org.zongf.utils.common.util;

import org.zongf.utils.common.exception.AssertException;

import java.util.Collection;
import java.util.Map;

/**
 * @Description: 断言工具类
 * @since 1.0
 * @author: zongf
 * @date: 2019-06-12 10:08
 */
public class AssertUtil {

    /**
     * @Description: 判断对象非空
     * @param object 要判断的对象, 可为任意类型
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:19:18
     */
    public static void notNull(Object object) {
        if (object == null) {
            throw new AssertException("对象为空!");
        }
    }

    /**
     * @Description: 校验字符串非空, null和一组空格均视为空
     * @param str 字符串
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:19:18
     */
    public static void notEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            throw new AssertException("字符串为空!");
        }
    }

    /**
     * @Description: 校验集合非空, null和0个元素均视为空
     * @param collection 集合对象, 可为List, Set, Collection等
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:19:18
     */
    public static void notEmpty(Collection collection) {
        if (collection == null || collection.size() == 0) {
            throw new AssertException("集合为空!");
        }
    }

    /**
     * @Description: 校验Map非空, null和0个元素均视为空
     * @param map map对象
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:19:18
     */
    public static void notEmpty(Map map) {
        if (map == null || map.size() == 0) {
            throw new AssertException("集合为空!");
        }
    }

    /**
     * @Description: 校验数组非空, null和0个元素均视为空
     * @param array 任意类型数组,非int[],long[]等基本类型数组
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:19:18
     */
    public static void notEmpty(Object[] array) {
        if (array == null || array.length == 0) {
            throw new AssertException("集合为空!");
        }
    }

    //************************************** 自定义异常信息 *****************************************

    /**
     * @Description: 判断对象非空
     * @param object 要判断的对象, 可为任意类型
     * @param exDesc 异常描述信息
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:19:18
     */
    public static void notNull(Object object, String exDesc) {
        if (object == null) {
            throw new AssertException(exDesc);
        }
    }


    /**
     * @Description: 校验字符串非空, null和一组空格均视为空
     * @param str 字符串
     * @param exDesc 异常描述信息
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:19:18
     */
    public static void notEmpty(String str, String exDesc) {
        if (str == null || "".equals(str.trim())) {
            throw new AssertException(exDesc);
        }
    }

    /**
     * @Description: 校验集合非空, null和0个元素均视为空
     * @param collection 集合对象, 可为List, Set, Collection等
     * @param exDesc 异常描述信息
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:19:18
     */
    public static void notEmpty(Collection collection, String exDesc) {
        if (collection == null || collection.size() == 0) {
            throw new AssertException(exDesc);
        }
    }

    /**
     * @Description: 校验Map非空, null和0个元素均视为空
     * @param map map对象
     * @param exDesc 异常描述信息
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:19:18
     */
    public static void notEmpty(Map map, String exDesc) {
        if (map == null || map.size() == 0) {
            throw new AssertException(exDesc);
        }
    }

    /**
     * @Description: 校验数组非空, null和0个元素均视为空
     * @param array 任意类型数组,非int[],long[]等基本类型数组
     * @param exDesc 异常描述信息
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 10:19:18
     */
    public static void notEmpty(Object[] array, String exDesc) {
        if (array == null || array.length == 0) {
            throw new AssertException(exDesc);
        }
    }

}
