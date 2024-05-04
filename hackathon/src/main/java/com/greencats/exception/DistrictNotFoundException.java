package com.greencats.exception;

import org.springframework.http.HttpStatus;

public class DistrictNotFoundException extends HackathonException {

    public DistrictNotFoundException() {
        super("DistrictNotFoundException", "Город не найден", HttpStatus.NOT_FOUND);
    }
}
