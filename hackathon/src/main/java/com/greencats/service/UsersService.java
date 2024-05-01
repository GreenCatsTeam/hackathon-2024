package com.greencats.service;

import com.greencats.hackathon.model.CreateUserRequest;
import com.greencats.hackathon.model.EditUserRequest;
import com.greencats.hackathon.model.IdResponse;
import org.springframework.http.ResponseEntity;

public interface UsersService {

    ResponseEntity<IdResponse> usersIdDelete(Long id);

    ResponseEntity<IdResponse> usersIdPut(Long id, EditUserRequest editUserRequest);

    ResponseEntity<IdResponse> usersPost(CreateUserRequest createUserRequest);
}
