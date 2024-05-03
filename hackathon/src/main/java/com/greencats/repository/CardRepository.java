package com.greencats.repository;

import com.greencats.dto.card.CardCreateInfo;
import com.greencats.dto.card.CardEditInfo;

public interface CardRepository {
    Long createCard(CardCreateInfo cardCreateInfo);
    Long updateCard(CardEditInfo cardEditInfo);

}
