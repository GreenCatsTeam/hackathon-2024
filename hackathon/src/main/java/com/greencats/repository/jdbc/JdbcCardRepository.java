package com.greencats.repository.jdbc;

import com.greencats.dto.card.CardCreateInfo;
import com.greencats.dto.card.CardEditInfo;
import com.greencats.exception.UserNotFoundException;
import com.greencats.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcCardRepository implements CardRepository {
    private final JdbcClient client;

    private static final String COMPLEXITY_FIELD = "complexity";

    @Override
    public Long createCard(CardCreateInfo cardCreateInfo) {
        boolean cardExists = false; //TODO: Add check for cards in defined area

        return client.sql("INSERT INTO Card(complexity, comment, photo, latitude, longtitude, points, city_id)" +
            "VALUES(:complexity, :comment, :photo, :latitude, :longtitude, :points, :city_id) RETURNING card_id")
            .param("complexity", cardCreateInfo.complexity())
            .param("comment", cardCreateInfo.comment())
            .param("photo", cardCreateInfo.photo())
            .param("latitude", cardCreateInfo.latitude())
            .param("longtitude", cardCreateInfo.longitude())
            .param("points", cardCreateInfo.points())
            .param("city_id", cardCreateInfo.city_id())
            .query(Long.class)
            .optional().orElseThrow(() -> new RuntimeException("Failed to create card for unknown reason"));
    }

    @Override
    public Long updateCard(CardEditInfo cardEditInfo) {
        int updatedRows = client.sql("UPDATE Card SET complexity = :complexity WHERE card_id = :card_id")
            .param("complexity", cardEditInfo.complexity())
            .param("card_id", cardEditInfo.card_id())
            .update();

        if (updatedRows == 0) {
            throw new UserNotFoundException();
        }

        return null;
    }



}
