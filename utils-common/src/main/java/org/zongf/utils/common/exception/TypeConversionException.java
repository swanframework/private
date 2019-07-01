package org.zongf.utils.common.exception;

/** 类型转换异常
 * @since 1.0
 * @author zongf
 * @created 2019-07-01
 */
public class TypeConversionException extends RuntimeException {

    public TypeConversionException() {
    }

    public TypeConversionException(String message) {
        super(message);
    }

    public TypeConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeConversionException(Throwable cause) {
        super(cause);
    }

    public TypeConversionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
