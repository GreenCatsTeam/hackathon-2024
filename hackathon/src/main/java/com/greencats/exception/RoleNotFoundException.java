package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends HackathonException {

    public RoleNotFoundException() {
        super("RoleNotFoundException", "Роль не найдена", HttpStatus.NOT_FOUND);
    }
}
