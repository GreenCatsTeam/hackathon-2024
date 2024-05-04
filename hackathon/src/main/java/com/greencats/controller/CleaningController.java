package com.greencats.controller;

import com.greencats.hackathon.api.CleaningApi;
import com.greencats.hackathon.model.CleaningRequest;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.service.CleaningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CleaningController implements CleaningApi {
    private final CleaningService cleaningService;

    @Override public ResponseEntity<IdResponse> createCleaning(CleaningRequest cleaningRequest){
        return cleaningService.createCleaning(cleaningRequest);
    }
}
