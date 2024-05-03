package com.greencats.controller;

import com.greencats.hackathon.api.AuthApi;
import com.greencats.hackathon.model.AuthorizeUserRequest;
import com.greencats.hackathon.model.JWTToken;
import com.greencats.hackathon.model.RegisterUserRequest;
import com.greencats.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<JWTToken> performLogin(AuthorizeUserRequest authorizeUserRequest) {
        return authService.performLogin(authorizeUserRequest.getEmail(), authorizeUserRequest.getPassword());
    }

    @Override
    public ResponseEntity<JWTToken> performRegistration(RegisterUserRequest registerUserRequest) {
        return authService.performRegistration(registerUserRequest.getEmail(), registerUserRequest.getPassword());
    }
}
