package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class UserBadCredentialException extends HackathonException {

    public UserBadCredentialException() {
        super("UserBadCredentialException", "Неправильная почта или пароль", HttpStatus.CONFLICT);
    }
}
