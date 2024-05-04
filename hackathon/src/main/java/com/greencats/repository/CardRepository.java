package com.greencats.repository;

import com.greencats.dto.card.CardCreateInfo;
import com.greencats.dto.card.CardEditInfo;
import com.greencats.dto.card.CardInfo;
import com.greencats.dto.card.ShortCardInfo;
import com.greencats.hackathon.model.CountResponse;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface CardRepository {
    Long createCard(CardCreateInfo cardCreateInfo);

    Long updateCard(CardEditInfo cardEditInfo);

    void deleteCard(Long id);

    CardInfo getCard(Long id);

    List<ShortCardInfo> getListCards(Integer limit, Integer offset);

    Integer approveCard(Long id);
}
