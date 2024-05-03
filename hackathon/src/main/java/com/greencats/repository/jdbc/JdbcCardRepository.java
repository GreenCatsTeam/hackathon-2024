package com.greencats.repository.jdbc;

import com.greencats.dto.card.CardCreateInfo;
import com.greencats.dto.card.CardEditInfo;
import com.greencats.dto.card.CardInfo;
import com.greencats.dto.card.ShortCardInfo;
import com.greencats.exception.CardNotCreatedException;
import com.greencats.exception.CardNotFoundException;
import com.greencats.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcCardRepository implements CardRepository {
    private final JdbcClient client;

    private static final String COMPLEXITY_FIELD = "complexity";

    @Override
    public Long createCard(CardCreateInfo cardCreateInfo) {
        boolean cardExists = false; //TODO: Add check for cards in defined area

        Long cardId = client.sql(
                "INSERT INTO Card(complexity, comment, photo, latitude, longitude, points, city_id, district_id)" +
                    "VALUES(:complexity, :comment, :photo, :latitude, :longtitude, :points, :city_id, :district_id) RETURNING card_id")
            .param("complexity", cardCreateInfo.complexity())
            .param("comment", cardCreateInfo.comment())
            .param("photo", cardCreateInfo.photo())
            .param("latitude", cardCreateInfo.latitude())
            .param("longtitude", cardCreateInfo.longitude())
            .param("points", cardCreateInfo.points())
            .param("city_id", cardCreateInfo.cityId())
            .param("district_id", cardCreateInfo.districtId())
            .query(Long.class)
            .optional().orElseThrow(CardNotCreatedException::new);

        client.sql(
                "INSERT INTO cleaning(card_id, status_id, user_id, time) VALUES (:card_id, :status_id, :user_id, :time)")
            .params("card_id", cardId, "status_id", 0, "user_id", cardCreateInfo.userId(), ZonedDateTime.now());

        return cardId;
    }

    @Override
    public Long updateCard(CardEditInfo cardEditInfo) {
        int updatedRows = client.sql("UPDATE Card SET complexity = :complexity WHERE card_id = :card_id")
            .param("complexity", cardEditInfo.complexity())
            .param("card_id", cardEditInfo.cardId())
            .update();

        if (updatedRows == 0) {
            throw new CardNotFoundException();
        }

        return cardEditInfo.cardId();
    }

    @Override
    public void deleteCard(Long id) {
        int updatedRows = client.sql("UPDATE Card SET is_deleted = TRUE WHERE card_id = :card_id")
            .param("card_id", id)
            .update();

        if (updatedRows == 0) {
            throw new CardNotFoundException();
        }
    }

    @Override
    public CardInfo getCard(Long id) {
        return client.sql(
                "SELECT card_id, user_id, complexity, comment, photo, latitude, longitude, points, city_id FROM Card " +
                    "INNER JOIN Cleaning ON card.card_id = cleaning.card_id " +
                    "WHERE card_id = :card_id")
            .param("card_id", id)
            .query(CardInfo.class)
            .optional().orElseThrow(CardNotFoundException::new);
    }

    @Override
    public List<ShortCardInfo> getListCards(Integer limit, Integer offset) {
        return client.sql("SELECT ");
    }

}
