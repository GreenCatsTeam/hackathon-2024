package com.greencats.controller;

import com.greencats.hackathon.api.UsersApi;
import com.greencats.hackathon.model.CardListInfo;
import com.greencats.hackathon.model.EditUserRequest;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.service.UsersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UsersController implements UsersApi {

    private final UsersService usersService;

    @Override
    @SecurityRequirement(name = "basic")
    public ResponseEntity<IdResponse> usersIdDelete(Long id) {
        return usersService.usersIdDelete(id);
    }

    @Override
    @SecurityRequirement(name = "basic")
    public ResponseEntity<IdResponse> usersIdPut(Long id, EditUserRequest editUserRequest) {
        return usersService.usersIdPut(id, editUserRequest);
    }

    @Override
    public ResponseEntity<List<CardListInfo>> getUserCardsList(Long id, Integer limit, Integer offset) {
        return usersService.getUserCardsList(limit, offset, id);
    }
}
