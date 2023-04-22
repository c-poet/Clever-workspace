package cn.cpoet.blog.auth.exception;

/**
 * @author CPoet
 */
public class NotAuthException extends AuthException {
    private static final long serialVersionUID = -5639965903895374562L;

    public NotAuthException() {
        super("The current subject is not authenticated and the access fails.");
    }

    public NotAuthException(String message) {
        super(message);
    }

    public NotAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthException(Throwable cause) {
        super(cause);
    }
}
