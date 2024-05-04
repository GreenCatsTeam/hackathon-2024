package com.greencats.service.jdbc;

import com.greencats.dto.nearcards.Coordinates;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.repository.NearCardsRepository;
import com.greencats.service.NearCardsService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcNearCardsService implements NearCardsService {

    private final NearCardsRepository nearCardsRepository;

    @Override
    public ResponseEntity<List<IdResponse>> getNearCards(Double x1, Double y1, Double x2, Double y2) {
        Coordinates coordinates = new Coordinates(x1, x2, y1, y2);
        List<Long> nearCards = nearCardsRepository.getNearCards(coordinates);

        List<IdResponse> cardsResult = new ArrayList<>();
        for (long card : nearCards) {
            IdResponse idResponse = new IdResponse();
            idResponse.setId(card);
            cardsResult.add(idResponse);
        }
        return new ResponseEntity<>(cardsResult, HttpStatus.OK);
    }
}
