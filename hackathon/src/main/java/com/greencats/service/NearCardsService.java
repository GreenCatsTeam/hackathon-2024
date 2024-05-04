package com.greencats.service;

import com.greencats.hackathon.model.IdResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NearCardsService {

    ResponseEntity<List<IdResponse>> getNearCards(Double x1, Double y1, Double x2, Double y2);
}
