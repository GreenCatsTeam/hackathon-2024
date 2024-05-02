package com.greencats.service.jdbc;

import com.greencats.dto.user.UserCreateInfo;
import com.greencats.dto.user.UserEditInfo;
import com.greencats.hackathon.model.CreateUserRequest;
import com.greencats.hackathon.model.EditUserRequest;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.repository.UsersRepository;
import com.greencats.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcUsersService implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    public ResponseEntity<IdResponse> usersIdDelete(Long id) {
        IdResponse idResponse = new IdResponse();
        idResponse.setId(usersRepository.usersIdDelete(id));

        return new ResponseEntity<>(idResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<IdResponse> usersIdPut(Long id, EditUserRequest editUserRequest) {
        IdResponse idResponse = new IdResponse();
        idResponse.setId(usersRepository.usersIdPut(
            id,
            new UserEditInfo(editUserRequest.getEmail(), editUserRequest.getPassword())
        ));

        return new ResponseEntity<>(idResponse, HttpStatus.OK);
    }

    @Override
    @SneakyThrows
    public ResponseEntity<IdResponse> usersPost(CreateUserRequest createUserRequest) {
        IdResponse idResponse = new IdResponse();
        idResponse.setId(
            usersRepository.usersPost(
                new UserCreateInfo(
                    createUserRequest.getEmail(),
                    createUserRequest.getPassword()
                )
            )
        );

        return new ResponseEntity<>(idResponse, HttpStatus.OK);
    }
}
