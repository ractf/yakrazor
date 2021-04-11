package uk.co.ractf.yakrazor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException() {
    }

    public ApplicationNotFoundException(final String message) {
        super(message);
    }

    public ApplicationNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ApplicationNotFoundException(final Throwable cause) {
        super(cause);
    }

    public ApplicationNotFoundException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
