package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class CleaningNotFoundException extends HackathonException {

    public CleaningNotFoundException(String description, String message, HttpStatus status) {
        super("CleaningNotFoundException", "Cleaning не найден", HttpStatus.NOT_FOUND);
    }
}
