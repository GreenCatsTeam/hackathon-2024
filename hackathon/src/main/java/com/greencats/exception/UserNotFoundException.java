package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends HackathonException {

    public UserNotFoundException(Long id) {
        super("UserNotFoundException", String.format("Пользователь %s не найден", id), HttpStatus.NOT_FOUND);
    }
}
