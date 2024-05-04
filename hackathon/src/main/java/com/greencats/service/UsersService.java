package com.greencats.service;

import com.greencats.hackathon.model.CardListInfo;
import com.greencats.hackathon.model.EditUserRequest;
import com.greencats.hackathon.model.IdResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface UsersService {

    ResponseEntity<IdResponse> usersIdDelete(Long id);

    ResponseEntity<IdResponse> usersIdPut(Long id, EditUserRequest editUserRequest);

    ResponseEntity<List<CardListInfo>> getUserCardsList(Integer limit, Integer offset, Long id);
}
