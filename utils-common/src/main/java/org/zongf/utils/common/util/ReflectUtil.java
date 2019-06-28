package org.zongf.utils.common.util;

import org.zongf.utils.common.exception.ReflectException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/** 反射工具类
 * @since 1.0
 * @author zongf
 * @created 2019-06-12
 */
public class ReflectUtil {

    /**为对象属性赋值, 通过属性setter方法.
     * @param target 目标对象
     * @param property 属性名
     * @param value 属性值, 类型必须和属性类型一致
     * @throws Exception 属性没有set方法或set方法不可访问, 抛出异常
     * @since 1.0
     * @author zongf
     * @created 2019-06-12
     */
    public static void setValueByWriteMethod(Object target, String property, Object value) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(property, target.getClass());
            Method writeMethod = descriptor.getWriteMethod();
            writeMethod.invoke(target, value);
        } catch (Exception e) {
            throw new ReflectException("对象属性赋值异常!",e);
        }
    }

    /**获取对象属性值, 通过属性getter方法.
     * @param target 目标对象
     * @param property 属性
     * @return Object 返回对象属性值, 可强转换为属性真实类型
     * @throws Exception 属性没有getter方法, 或getter方法不可访问时,抛出异常
     * @since 1.0
     * @author zongf
     * @created 2019-06-12
     */
    public static Object getValueByReadMethod(Object target, String property) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(property, target.getClass());
            Method readMethod = descriptor.getReadMethod();
            return readMethod.invoke(target);
        } catch (Exception e) {
            throw new ReflectException("获取对象属性值异常",e);
        }
    }

    /**为对象属性赋值, 通过属性直接赋值
     * @param target 目标对象
     * @param property 属性名
     * @param value 类型必须和属性类型一致
     * @since 1.0
     * @author zongf
     * @created 2019-06-12
     */
    public static void setValueByField(Object target, String property, Object value) {

        Field declaredField = null;

        // 是否修改了Accessible 属性
        boolean changeAccessible = false;

        try {
            declaredField = target.getClass().getDeclaredField(property);

            // 属性不可访问, 则设置可访问
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }

            declaredField.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("对象属性赋值异常!",e);
        } finally {
            // 如果字段不为空, 且修改了访问属性, 则回滚访问属性
            if (declaredField != null && changeAccessible) {
                declaredField.setAccessible(false);
            }
        }
    }

    /**获取对象属性值, 通过属性直接获取
     * @param target 目标对象
     * @param property 属性名
     * @return: Object 返回属性实际类型,可强转为属性真实类型
     * @since 1.0
     * @author zongf
     * @created 2019-06-12
     */
    public static Object getValueByField(Object target, String property) {

        Field declaredField = null;

        // 是否修改了Accessible 属性
        boolean changeAccessible = false;
        
        try {
            declaredField = target.getClass().getDeclaredField(property);

            // 属性不可访问, 则设置可访问
            if (!declaredField.isAccessible()) {
                declaredField.setAccessible(true);
            }

            return declaredField.get(target);
        } catch (Exception e) {
            throw new ReflectException("获取对象属性值异常!",e);
        } finally {
            // 如果字段不为空, 且修改了访问属性, 则回滚访问属性
            if (declaredField != null && changeAccessible) {
                declaredField.setAccessible(false);
            }
        }
    }

}
