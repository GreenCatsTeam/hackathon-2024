package com.greencats.controller;

import com.greencats.hackathon.api.CardsApi;
import com.greencats.hackathon.model.CardListInfo;
import com.greencats.hackathon.model.CardRequest;
import com.greencats.hackathon.model.CardResponse;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.hackathon.model.UpdateCardRequest;
import com.greencats.service.CardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardsController implements CardsApi {

    private final CardsService cardsService;

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
}
