package com.udacity.jdnd.course3.critter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoDataFoundForRequest extends RuntimeException {
    public NoDataFoundForRequest() {
        super();
    }

    public NoDataFoundForRequest(String message) {
        super(message);
    }

    public NoDataFoundForRequest(String message, Throwable cause) {
        super(message, cause);
    }

}
