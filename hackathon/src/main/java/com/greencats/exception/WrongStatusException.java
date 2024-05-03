package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class WrongStatusException extends HackathonException {

    public WrongStatusException() {
        super("WrongStatusException", "Произошла ошибка при назначении статуса", HttpStatus.NOT_FOUND);
    }
}
