package uk.co.ractf.yakrazor.exception;

public class DeploymentNotFoundException extends RuntimeException {
    public DeploymentNotFoundException() {
    }

    public DeploymentNotFoundException(final String message) {
        super(message);
    }

    public DeploymentNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DeploymentNotFoundException(final Throwable cause) {
        super(cause);
    }

    public DeploymentNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
