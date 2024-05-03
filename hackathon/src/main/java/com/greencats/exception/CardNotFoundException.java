package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class CardNotFoundException extends HackathonException {

    public CardNotFoundException() {
        super("CardNotFoundException", String.format("Карта не найдена"), HttpStatus.NOT_FOUND);
    }
}
