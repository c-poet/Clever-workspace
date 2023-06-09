package cn.cpoet.workspace.api.exception;

/**
 * 应用异常
 *
 * @author CPoet
 */
public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(Throwable cause) {
        super(cause);
    }
}
