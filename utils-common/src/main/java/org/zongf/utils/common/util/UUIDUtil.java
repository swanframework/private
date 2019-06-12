package org.zongf.utils.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Description: 获取UUID 字符串
 * @since 1.0
 * @author: zongf
 * @date: 2019-06-12 11:02
 */
public class UUIDUtil {

    /**
     * @Description: 获取随机的UUID字符串, 32位小写字母与数字
     * @return: String
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 11:14:30
     */
    public static String getLowerString(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * @Description: 获取随机的UUID字符串, 32位大写字母与数字
     * @return: String
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 11:14:30
     */
    public static String getUpperString() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * @Description: 批量获取随机UUID字符串, 32位小写字母与数字
     * @param num 数量
     * @return: List
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 11:14:30
     */
    public static List<String> getLowerStrings(int num) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(getLowerString());
        }
        return list;
    }

    /**
     * @Description: 批量获取随机UUID字符串, 32位大写字母与数字
     * @param num 数量
     * @return: List
     * @since 1.0
     * @author: zongf
     * @time: 2019-06-12 11:14:30
     */
    public static List<String> getUpperStrings(int num) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(getUpperString());
        }
        return list;
    }
}


