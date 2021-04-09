package uk.co.ractf.yakrazor.exception;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException() {
    }

    public ServiceNotFoundException(final String message) {
        super(message);
    }

    public ServiceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ServiceNotFoundException(final Throwable cause) {
        super(cause);
    }

    public ServiceNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
