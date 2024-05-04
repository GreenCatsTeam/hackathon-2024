package com.greencats.service;

import com.greencats.hackathon.model.CleaningRequest;
import com.greencats.hackathon.model.IdResponse;
import org.springframework.http.ResponseEntity;

public interface CleaningService {
    ResponseEntity<IdResponse> createCleaning(CleaningRequest cleaningRequest);
}
