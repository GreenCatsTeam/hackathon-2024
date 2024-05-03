package com.greencats.service.jdbc;

import com.greencats.dto.user.UserEditInfo;
import com.greencats.hackathon.model.EditUserRequest;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.repository.UsersRepository;
import com.greencats.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcUsersService implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public ResponseEntity<IdResponse> usersIdDelete(Long id) {
        IdResponse idResponse = new IdResponse();
        idResponse.setId(usersRepository.usersIdDelete(id));

        return new ResponseEntity<>(idResponse, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<IdResponse> usersIdPut(Long id, EditUserRequest editUserRequest) {
        IdResponse idResponse = new IdResponse();
        idResponse.setId(usersRepository.usersIdPut(
            id,
            new UserEditInfo(editUserRequest.getEmail(), editUserRequest.getPassword())
        ));

        return new ResponseEntity<>(idResponse, HttpStatus.OK);
    }
}
