package org.zongf.utils.common.exception;

/** 序列化异常
 * @since 1.0
 * @author zongf
 * @created 2019-07-24
 */
public class SerializableException extends RuntimeException {

    public SerializableException() {
        super();
    }

    public SerializableException(String message) {
        super(message);
    }

    public SerializableException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializableException(Throwable cause) {
        super(cause);
    }

    protected SerializableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
