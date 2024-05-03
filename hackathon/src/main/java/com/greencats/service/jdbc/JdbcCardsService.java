package com.greencats.service.jdbc;

import com.greencats.hackathon.model.CardListInfo;
import com.greencats.hackathon.model.CardRequest;
import com.greencats.hackathon.model.CardResponse;
import com.greencats.hackathon.model.ComplexityChangeRequest;
import com.greencats.service.CardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcCardsService implements CardsService {

    @Override
    public ResponseEntity<List<CardListInfo>> getListCards(Integer limit, Integer offset) {
        return null;
    }

    @Override
    public ResponseEntity<CardResponse> changeCardComplexity(Long id, ComplexityChangeRequest complexityChangeRequest) {
        return null;
    }

    @Override
    public ResponseEntity<CardResponse> createCard(CardRequest cardRequest) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteCard(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<CardResponse> getCard(Long id) {
        return null;
    }
}
