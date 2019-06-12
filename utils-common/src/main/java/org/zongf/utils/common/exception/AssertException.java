package org.zongf.utils.common.exception;

/**
 * @Description: 断言异常
 * @since 1.0
 * @author: zongf
 * @date: 2019-06-12 10:09
 */
public class AssertException extends RuntimeException {

    public AssertException() {
    }

    public AssertException(String message) {
        super(message);
    }

    public AssertException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertException(Throwable cause) {
        super(cause);
    }

    public AssertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
