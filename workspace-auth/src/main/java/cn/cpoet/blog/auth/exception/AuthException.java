package cn.cpoet.blog.auth.exception;

import cn.cpoet.blog.api.exception.AppException;

/**
 * @author CPoet
 */
public class AuthException extends AppException {

    private static final long serialVersionUID = -3567143231832082693L;

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }
}
