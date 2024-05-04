package com.greencats.controller;

import com.greencats.hackathon.api.NearCardsApi;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.service.NearCardsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NearCardsController implements NearCardsApi {

    private final NearCardsService nearCardsService;

    @Override
    public ResponseEntity<List<IdResponse>> getNearCards(Double x1, Double y1, Double x2, Double y2) {
        return nearCardsService.getNearCards(x1, y1, x2, y2);
    }
}
