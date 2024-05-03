package com.greencats.repository;

import com.greencats.dto.card.CardCreateInfo;
import com.greencats.dto.card.CardEditInfo;
import com.greencats.dto.card.CardInfo;
import java.util.Optional;

public interface CardRepository {
    Long createCard(CardCreateInfo cardCreateInfo);
    Long updateCard(CardEditInfo cardEditInfo);
    Optional<CardInfo> getCard(Long id);
}
