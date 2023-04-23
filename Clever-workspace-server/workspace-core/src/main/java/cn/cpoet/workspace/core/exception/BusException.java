package cn.cpoet.workspace.core.exception;

import cn.cpoet.workspace.api.exception.AppException;
import cn.cpoet.workspace.core.constant.Status;
import cn.cpoet.workspace.core.constant.StatusConst;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author CPoet
 */
public class BusException extends AppException {

    private static final long serialVersionUID = -8411013470375188671L;

    @Getter
    private final Status status;

    public BusException(String message) {
        this(StatusConst.FAILED, message);
    }

    public BusException(String message, Throwable cause) {
        this(StatusConst.FAILED, message, cause);
    }

    public BusException(Status status) {
        this(status, status.message());
    }

    public BusException(Status status, String message) {
        super(message);
        this.status = new InnerStatus(status.code(), message);
    }

    public BusException(Status status, String message, Throwable cause) {
        super(message, cause);
        this.status = new InnerStatus(status.code(), message);
    }

    @RequiredArgsConstructor
    private static class InnerStatus implements Status {

        private final String code;
        private final String message;

        @Override
        public String code() {
            return code;
        }

        @Override
        public String message() {
            return message;
        }
    }
}
