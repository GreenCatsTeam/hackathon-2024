package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends HackathonException {

    public UserAlreadyExistException() {
        super("UserAlreadyExistException", String.format("Пользователь уже существует"), HttpStatus.CONFLICT);
    }
}
