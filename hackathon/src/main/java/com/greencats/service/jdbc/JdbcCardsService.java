package com.greencats.service.jdbc;

import com.greencats.dto.card.CardCreateInfo;
import com.greencats.dto.card.CardEditInfo;
import com.greencats.hackathon.model.CardListInfo;
import com.greencats.hackathon.model.CardRequest;
import com.greencats.hackathon.model.CardResponse;
import com.greencats.hackathon.model.ComplexityChangeRequest;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.repository.CardRepository;
import com.greencats.service.CardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcCardsService implements CardsService {

    private final CardRepository cardRepository;

    @Override
    public ResponseEntity<List<CardListInfo>> getListCards(Integer limit, Integer offset) {
        return null;
    }

    @Override
    public ResponseEntity<CardResponse> changeCardComplexity(Long id, ComplexityChangeRequest complexityChangeRequest) {
        CardEditInfo cardEditInfo = new CardEditInfo(id, complexityChangeRequest.getComplexity());
        CardResponse cardResponce = cardRepository.updateCard(cardEditInfo);
    }

    @Override
    public ResponseEntity<IdResponse> createCard(CardRequest cardRequest) {
        CardCreateInfo cardCreateInfo = new CardCreateInfo(

            cardRequest.getComplexity(),
            cardRequest.getComment(),
            cardRequest.getPhoto(),
            cardRequest.getLatitude(),
            cardRequest.getLongitude(),
            cardRequest.getComplexity() * 2, // points magic math
            cardRequest.getCityId()
        );

        Long id = cardRepository.createCard(cardCreateInfo);
        IdResponse idResponse = new IdResponse();
        idResponse.setId(id);
        return new ResponseEntity<>(idResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteCard(Long id) {
        cardRepository.deleteCard(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CardResponse> getCard(Long id) {
        CardInfo cardInfo = cardRepository.getCard(id);

        CardResponse cardResponse = new CardResponse(
            cardInfo.getCardId(),
            cardInfo.getUserId(),
            cardInfo.getComplexity(),
            cardInfo.getComment(),
            cardInfo.getPhoto(),
            cardInfo.getLatitude(),
            cardInfo.getLongitude(),
            cardInfo.getPoints(), // points magic math
            cardInfo.getCityId()
        );
        return new ResponseEntity<>(cardResponse, HttpStatus.OK)
    }
}
