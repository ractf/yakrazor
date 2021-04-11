package uk.co.ractf.yakrazor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MissingDeploymentVariablesException extends ResponseStatusException {

    public MissingDeploymentVariablesException(final String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

}
