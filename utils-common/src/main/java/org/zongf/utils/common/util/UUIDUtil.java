package org.zongf.utils.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** UUID字符串工具类
 * @since 1.0
 * @author zongf
 * @created 2019-07-01
 */
public class UUIDUtil {

    /** 获取随机的UUID字符串, 32位小写字母与数字
     * @return 32位小写字母与数字组成的字符串, 不包含连接符-
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static String getLowerString(){
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**获取随机的UUID字符串, 32位大写字母与数字
     * @return 32位大写字母与数字组成的字符串, 不包含连接符-
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static String getUpperString() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /** 批量获取随机UUID字符串, 32位小写字母与数字
     * @param num UUID字符串数量
     * @return List UUID 字符串列表
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static List<String> getLowerStrings(int num) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(getLowerString());
        }
        return list;
    }

    /**批量获取随机UUID字符串, 32位大写字母与数字
     * @param num UUID字符串数量
     * @return List UUID 字符串列表
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static List<String> getUpperStrings(int num) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(getUpperString());
        }
        return list;
    }
}


