package com.greencats.service.jdbc;

import com.greencats.dto.card.ShortCardInfo;
import com.greencats.dto.user.UserEditInfo;
import com.greencats.hackathon.model.CardListInfo;
import com.greencats.hackathon.model.EditUserRequest;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.repository.UsersRepository;
import com.greencats.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public ResponseEntity<List<CardListInfo>> getUserCardsList(Integer limit, Integer offset, Long id) {
        List<ShortCardInfo> shortCardInfos = usersRepository.getUserCardsList(limit, offset, id);

        List<CardListInfo> cardListInfos = new ArrayList<>();
        for (ShortCardInfo cardInfo : shortCardInfos) {
            CardListInfo cardListInfo = new CardListInfo();
            cardListInfo.setCardId(cardInfo.cardId());
            cardListInfo.setComplexity(cardInfo.complexity());
            cardListInfo.setLongitude(cardInfo.longitude());
            cardListInfo.setLatitude(cardInfo.latitude());
            cardListInfo.setStatusId(cardInfo.statusId());
            cardListInfo.setCityId(cardInfo.cityId());
            cardListInfo.setDistrictId(cardInfo.districtId());
        }

        return new ResponseEntity<>(cardListInfos, HttpStatus.OK);
    }
}
