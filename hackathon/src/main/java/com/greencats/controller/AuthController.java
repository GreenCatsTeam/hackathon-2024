package com.greencats.controller;

import com.greencats.exception.UserBadCredentialException;
import com.greencats.hackathon.api.AuthApi;
import com.greencats.hackathon.model.AuthorizeUserRequest;
import com.greencats.hackathon.model.JWTToken;
import com.greencats.hackathon.model.RegisterUserRequest;
import com.greencats.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<JWTToken> performLogin(AuthorizeUserRequest authorizeUserRequest) {
        return authService.performLogin(authorizeUserRequest.getEmail(), authorizeUserRequest.getPassword());
    }

    @Override
    public ResponseEntity<JWTToken> performRegistration(RegisterUserRequest registerUserRequest) {
        if (registerUserRequest.getRole().equals("ADMIN")) {
            throw new UserBadCredentialException();
        }
        return authService.performRegistration(
            registerUserRequest.getFirstName(),
            registerUserRequest.getLastName(),
            registerUserRequest.getEmail(),
            registerUserRequest.getPassword(),
            registerUserRequest.getRole(),
            registerUserRequest.getOrganization(),
            registerUserRequest.getCityName(),
            registerUserRequest.getDistrictName()
        );
    }
}
