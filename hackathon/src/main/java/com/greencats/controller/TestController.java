package com.greencats.controller;

import com.greencats.hackathon.model.IdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@CrossOrigin("http://localhost:3000")
public class TestController {

    @RequestMapping(
        method = RequestMethod.GET,
        produces = { "application/json" }
    )
    @SuppressWarnings("MagicNumber")
    ResponseEntity<IdResponse> usersIdDelete() {
        IdResponse idResponse = new IdResponse();
        idResponse.setId(41L);
        return new ResponseEntity<>(idResponse, HttpStatus.OK);
    }
}
