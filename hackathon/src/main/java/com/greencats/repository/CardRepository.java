package com.greencats.repository;

import com.greencats.dto.card.CardCreateInfo;
import com.greencats.dto.card.CardEditInfo;
import com.greencats.dto.card.CardInfo;
import java.util.List;
import java.util.Optional;

public interface CardRepository {
    Long createCard(CardCreateInfo cardCreateInfo);
    Long updateCard(CardEditInfo cardEditInfo);
    Long deleteCard(Long id);
    CardInfo getCard(Long id);
    List<ShortCardInfo> getListCards(Integer limit, Integer offset);
}
