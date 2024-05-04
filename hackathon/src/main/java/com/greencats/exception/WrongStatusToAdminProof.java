package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class WrongStatusToAdminProof extends HackathonException {

    public WrongStatusToAdminProof() {
        super("WrongStatusToAdminProof", "Необходим статус 'модерация'", HttpStatus.BAD_REQUEST);
    }
}
