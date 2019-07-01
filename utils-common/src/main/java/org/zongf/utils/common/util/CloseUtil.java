package org.zongf.utils.common.util;


/** 流关闭工具类
 * @author: zongf
 * @created: 2019-07-01
 * @since 1.0
 */
public class CloseUtil {

    /**手工关闭流
     * @param closeables 任意多个实现AutoCloseable接口的对象
     * @since 1.0
     * @author zongf
     * @created 2019-07-01
     */
    public static void close(AutoCloseable... closeables) {
        if (closeables != null) {
            for (AutoCloseable closeable: closeables) {
                try {
                    closeable.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
