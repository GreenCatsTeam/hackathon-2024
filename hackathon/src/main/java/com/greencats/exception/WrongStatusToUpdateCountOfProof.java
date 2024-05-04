package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class WrongStatusToUpdateCountOfProof extends HackathonException {

    public WrongStatusToUpdateCountOfProof() {
        super("WrongStatusToUpdateCountOfProof", "Необходим статус 'на проверке'", HttpStatus.BAD_REQUEST);
    }
}
