package com.greencats.service.jdbc;

import com.greencats.dto.card.CardCreateInfo;
import com.greencats.dto.card.CardEditInfo;
import com.greencats.dto.card.CardInfo;
import com.greencats.dto.card.ShortCardInfo;
import com.greencats.hackathon.model.CardListInfo;
import com.greencats.hackathon.model.CardRequest;
import com.greencats.hackathon.model.CardResponse;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.hackathon.model.UpdateCardRequest;
import com.greencats.repository.CardRepository;
import com.greencats.service.CardsService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcCardsService implements CardsService {

    private final CardRepository cardRepository;

    @Override
    @Transactional
    public ResponseEntity<List<CardListInfo>> getListCards(Integer limit, Integer offset) {
        List<ShortCardInfo> shortCardInfos = cardRepository.getListCards(limit, offset);

        List<CardListInfo> cardListInfos = new ArrayList<>();
        for (ShortCardInfo cardInfo : shortCardInfos) {
            CardListInfo cardListInfo = new CardListInfo();
            cardListInfo.setCardId(cardInfo.cardId());
            cardListInfo.setComplexity(cardInfo.complexity());
            cardListInfo.setLongitude(cardInfo.longitude());
            cardListInfo.setLatitude(cardInfo.latitude());
            cardListInfo.setStatusId(cardInfo.maxStatus());
            cardListInfo.setCityName(cardInfo.cityName());
            cardListInfo.setDistrictName(cardInfo.districtName());
            cardListInfos.add(cardListInfo);
        }

        return new ResponseEntity<>(cardListInfos, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<IdResponse> updateCard(Long id, UpdateCardRequest updateCardRequest) {
        CardEditInfo cardEditInfo =
            new CardEditInfo(
                id,
                updateCardRequest.getComplexity(),
                updateCardRequest.getStatusId(),
                updateCardRequest.getUserId()
            );
        IdResponse idResponse = new IdResponse();
        Long cardId = cardRepository.updateCard(cardEditInfo);
        idResponse.setId(cardId);
        return new ResponseEntity<>(idResponse, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<IdResponse> createCard(CardRequest cardRequest) {
        CardCreateInfo cardCreateInfo = new CardCreateInfo(
            cardRequest.getUserId(),
            cardRequest.getComplexity(),
            cardRequest.getComment(),
            cardRequest.getPhoto(),
            cardRequest.getLatitude(),
            cardRequest.getLongitude(),
            cardRequest.getComplexity() * 2, // points magic math
            cardRequest.getCityName(),
            cardRequest.getDistrictName()
        );

        Long id = cardRepository.createCard(cardCreateInfo);
        IdResponse idResponse = new IdResponse();
        idResponse.setId(id);
        return new ResponseEntity<>(idResponse, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteCard(Long id) {
        cardRepository.deleteCard(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CardResponse> getCard(Long id) {
        CardInfo cardInfo = cardRepository.getCard(id);

        CardResponse cardResponse = new CardResponse();
        cardResponse.setCardId(cardInfo.cardId());
        cardResponse.setUserId(cardInfo.userId());
        cardResponse.setComplexity(cardInfo.complexity());
        cardResponse.setComment(cardInfo.comment());
        cardResponse.setPhoto(cardInfo.photo());
        cardResponse.setLatitude(cardInfo.latitude());
        cardResponse.setLongitude(cardInfo.longitude());
        cardResponse.setPoints(cardInfo.points());
        cardResponse.setStatusId(cardInfo.statusId());
        cardResponse.setCityName(cardInfo.cityName());
        cardResponse.setDistrictName(cardInfo.districtName());
        return new ResponseEntity<>(cardResponse, HttpStatus.OK);
    }
}
