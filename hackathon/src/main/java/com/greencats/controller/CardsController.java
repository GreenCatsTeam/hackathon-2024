package com.greencats.controller;

import com.greencats.hackathon.api.CardsApi;
import com.greencats.hackathon.model.CardListInfo;
import com.greencats.hackathon.model.CardRequest;
import com.greencats.hackathon.model.CardResponse;
import com.greencats.hackathon.model.ComplexityChangeRequest;
import com.greencats.hackathon.model.IdResponse;
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
    public ResponseEntity<IdResponse> changeCardComplexity(Long id, ComplexityChangeRequest complexityChangeRequest) {
        return cardsService.changeCardComplexity(id, complexityChangeRequest);
    }

    @Override
    public ResponseEntity<IdResponse> createCard(CardRequest cardRequest) {
        return CardsApi.super.createCard(cardRequest);
    }

    @Override
    public ResponseEntity<Void> deleteCard(Long id) {
        return CardsApi.super.deleteCard(id);
    }

    @Override
    public ResponseEntity<CardResponse> getCard(Long id) {
        return CardsApi.super.getCard(id);
    }
}
