package com.twilio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IllegalArgumentException extends ResponseStatusException {
    public IllegalArgumentException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
