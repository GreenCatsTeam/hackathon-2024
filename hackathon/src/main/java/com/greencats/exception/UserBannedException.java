package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class UserBannedException extends HackathonException {

    public UserBannedException() {
        super("UserBannedException", "Пользователь забанен", HttpStatus.BAD_REQUEST);
    }
}
