package com.greencats.controller;

import com.greencats.hackathon.api.CardsApi;
import com.greencats.hackathon.model.CardListInfo;
import com.greencats.hackathon.model.CardRequest;
import com.greencats.hackathon.model.CardResponse;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.hackathon.model.UpdateCardRequest;
import com.greencats.service.CardsService;
import com.greencats.service.NearCardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardsController implements CardsApi {

    private final CardsService cardsService;

    private final NearCardsService nearCardsService;

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
    public ResponseEntity<IdResponse> approveCard(Long id, CardRequest cardRequest) {
        return CardsApi.super.approveCard(id, cardRequest);
    }

    @Override
    public ResponseEntity<List<IdResponse>> getNearCards(Double x1, Double y1, Double x2, Double y2) {
        return nearCardsService.getNearCards(x1, y1, x2, y2);
    }
}
