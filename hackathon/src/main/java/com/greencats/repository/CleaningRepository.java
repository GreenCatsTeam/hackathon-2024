package com.greencats.repository;

import com.greencats.dto.cleaning.CleaningCreateInfo;

public interface CleaningRepository {
    Long createCleaning(CleaningCreateInfo cleaningCreateInfo);
}
