package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends HackathonException {

    public UserNotFoundException() {
        super("UserNotFoundException", "Пользователь не найден", HttpStatus.NOT_FOUND);
    }
}
