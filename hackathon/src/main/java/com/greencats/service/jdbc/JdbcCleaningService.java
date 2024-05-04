package com.greencats.service.jdbc;

import com.greencats.dto.card.CardCreateInfo;
import com.greencats.dto.cleaning.CleaningCreateInfo;
import com.greencats.hackathon.model.CleaningRequest;
import com.greencats.hackathon.model.IdResponse;
import com.greencats.repository.CleaningRepository;
import com.greencats.service.CleaningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JdbcCleaningService implements CleaningService {
    private final CleaningRepository cleaningRepository;

    @Override
    @Transactional
    public ResponseEntity<IdResponse> createCleaning(CleaningRequest cleaningRequest) {
        CleaningCreateInfo cleaningCreateInfo = new CleaningCreateInfo (
            cleaningRequest.getCardId(),
            cleaningRequest.getUserId()
        );

        Long id = cleaningRepository.createCleaning(cleaningCreateInfo);
        IdResponse idResponse = new IdResponse();
        idResponse.setId(id);
        return new ResponseEntity<>(idResponse, HttpStatus.OK);
    }
}
