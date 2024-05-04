package com.greencats.controller;

import com.greencats.hackathon.api.CardsApi;
import com.greencats.hackathon.model.CardListInfo;
import com.greencats.hackathon.model.CardRequest;
import com.greencats.hackathon.model.CardResponse;
import com.greencats.hackathon.model.CleaningRequest;
import com.greencats.hackathon.model.CountResponse;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.hackathon.model.UpdateCardRequest;
import com.greencats.service.CardsService;
import com.greencats.service.CleaningService;
import com.greencats.service.NearCardsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class CardsController implements CardsApi {

    private final CardsService cardsService;

    private final NearCardsService nearCardsService;

    private final CleaningService cleaningService;

    @Override
    public ResponseEntity<List<CardListInfo>> getListCards(Integer limit, Integer offset) {
        return cardsService.getListCards(limit, offset);
    }

    @Override
    public ResponseEntity<IdResponse> updateCard(Long id, UpdateCardRequest updateCardRequest) {
        return cardsService.updateCard(id, updateCardRequest);
    }

    @Override
    public ResponseEntity<IdResponse> createCard(CardRequest cardRequest) {
        return cardsService.createCard(cardRequest);
    }

    @Override
    public ResponseEntity<Void> deleteCard(Long id) {
        return cardsService.deleteCard(id);
    }

    @Override
    public ResponseEntity<CardResponse> getCard(Long id) {
        return cardsService.getCard(id);
    }

    @Override
    public ResponseEntity<CountResponse> approveCard(Long id) {
        return cardsService.approveCard(id);
    }

    @Override
    public ResponseEntity<List<IdResponse>> getNearCards(Double x1, Double y1, Double x2, Double y2) {
        return nearCardsService.getNearCards(x1, y1, x2, y2);
    }

    @Override
    public ResponseEntity<IdResponse> addUserToCleaning(CleaningRequest cleaningRequest) {
        return cleaningService.createCleaning(cleaningRequest);
    }
}
