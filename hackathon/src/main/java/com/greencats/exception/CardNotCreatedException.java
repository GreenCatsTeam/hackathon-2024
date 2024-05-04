package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class CardNotCreatedException extends HackathonException {

    public CardNotCreatedException() {
        super("CardNotCreatedException", "Карта не создана", HttpStatus.BAD_REQUEST);
    }
}
