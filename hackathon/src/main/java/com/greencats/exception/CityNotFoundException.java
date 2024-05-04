package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class CityNotFoundException extends HackathonException {

    public CityNotFoundException() {
        super("CityNotFoundException", "Город не найден", HttpStatus.NOT_FOUND);
    }
}
