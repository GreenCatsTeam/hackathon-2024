package com.greencats.controller;

import com.greencats.hackathon.api.CardsApi;
import com.greencats.hackathon.model.CardListInfo;
import com.greencats.hackathon.model.CardRequest;
import com.greencats.hackathon.model.CardResponse;
import com.greencats.hackathon.model.ComplexityChangeRequest;
import org.springframework.http.ResponseEntity;
import java.util.List;

public class CardsController implements CardsApi {

    @Override
    public ResponseEntity<List<CardListInfo>> getListCards(Integer limit, Integer offset) {
        return CardsApi.super.getListCards(limit, offset);
    }

    @Override
    public ResponseEntity<CardResponse> changeCardComplexity(Long id, ComplexityChangeRequest complexityChangeRequest) {
        return CardsApi.super.changeCardComplexity(id, complexityChangeRequest);
    }

    @Override
    public ResponseEntity<CardResponse> createCard(CardRequest cardRequest) {
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
