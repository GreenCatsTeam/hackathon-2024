package com.greencats.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HackathonException extends RuntimeException {
    private final String description;
    private final HttpStatus status;

    public HackathonException(String description, String message, HttpStatus status) {
        super(message);
        this.description = description;
        this.status = status;
    }
}
