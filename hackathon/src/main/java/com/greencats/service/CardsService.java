package com.greencats.service;

import com.greencats.hackathon.api.CardsApi;
import com.greencats.hackathon.model.CardListInfo;
import com.greencats.hackathon.model.CardRequest;
import com.greencats.hackathon.model.CardResponse;
import com.greencats.hackathon.model.ComplexityChangeRequest;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface CardsService {
    ResponseEntity<List<CardListInfo>> getListCards(Integer limit, Integer offset);

    ResponseEntity<CardResponse> changeCardComplexity(Long id, ComplexityChangeRequest complexityChangeRequest);

    ResponseEntity<CardResponse> createCard(CardRequest cardRequest);

    ResponseEntity<Void> deleteCard(Long id);

    ResponseEntity<CardResponse> getCard(Long id);
}
