package com.greencats.repository.jdbc;

import com.greencats.dto.card.CardCreateInfo;
import com.greencats.dto.card.CardEditInfo;
import com.greencats.dto.card.CardInfo;
import com.greencats.dto.card.ShortCardInfo;
import com.greencats.exception.CardNotCreatedException;
import com.greencats.exception.CardNotFoundException;
import com.greencats.exception.WrongStatusException;
import com.greencats.repository.CardRepository;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcCardRepository implements CardRepository {
    private final JdbcClient client;

    private final JdbcUsersRepository usersRepository;
    private static final String COMPLEXITY_FIELD = "complexity";

    @Override
    public Long createCard(CardCreateInfo cardCreateInfo) {
        boolean cardExists = false; //TODO: Add check for cards in defined area

        Long cardId = client.sql(
                "INSERT INTO Card(complexity, comment, photo, latitude, longitude, points, city_id, district_id)" +
                    "VALUES(:complexity, :comment, :photo, :latitude, :longitude, :points, :city_id, :district_id) RETURNING card_id")
            .param("complexity", cardCreateInfo.complexity())
            .param("comment", cardCreateInfo.comment())
            .param("photo", cardCreateInfo.photo())
            .param("latitude", cardCreateInfo.latitude())
            .param("longitude", cardCreateInfo.longitude())
            .param("points", cardCreateInfo.points())
            .param("city_id", cardCreateInfo.cityId())
            .param("district_id", cardCreateInfo.districtId())
            .query(Long.class)
            .optional().orElseThrow(CardNotCreatedException::new);

        if (usersRepository.isExistUserById(cardCreateInfo.userId())) {

            client.sql(
                    "INSERT INTO cleaning(card_id, status_id, user_id, time) VALUES (:card_id, :status_id, :user_id, :time)")
                .param("card_id", cardId)
                .param("status_id", 1)
                .param("user_id", cardCreateInfo.userId())
                .param("time", Timestamp.from(ZonedDateTime.now().toInstant()))
                .update();

            client.sql("INSERT INTO maxstatus(card_id, max_status) Values (:card_id, :max_status)")
                .param("card_id", cardId)
                .param("max_status", 1)
                .update();

            return cardId;
        }
        throw new CardNotCreatedException();
    }

    @Override
    public Long updateCard(CardEditInfo cardEditInfo) {
        Long maxStatus = client.sql("SELECT maxstatus.max_status from maxstatus where card_id = :card_id")
            .param("card_id", cardEditInfo.cardId())
            .query(Long.class)
            .optional().orElseThrow(CardNotFoundException::new);

        if (cardEditInfo.statusId() < maxStatus || cardEditInfo.statusId() < 1 || cardEditInfo.statusId() >= 4) {
            throw new WrongStatusException();
        }

        client.sql(
                "INSERT INTO cleaning(card_id, status_id, user_id, time) Values (:card_id, :status_id, :user_id, :time)")
            .param("card_id", cardEditInfo.cardId())
            .param("status_id", cardEditInfo.statusId())
            .param("user_id", cardEditInfo.userId())
            .param("time", Timestamp.from(ZonedDateTime.now().toInstant()))
            .update();

        int updatedRows = client.sql("UPDATE Card SET complexity = :complexity WHERE card_id = :card_id")
            .param("complexity", cardEditInfo.complexity())
            .param("card_id", cardEditInfo.cardId())
            .update();

        client.sql("UPDATE maxstatus SET max_status = :max_status WHERE card_id = :card_id")
            .param("card_id", cardEditInfo.cardId())
            .param("max_status", maxStatus + 1)
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
                "SELECT card.card_id, user_id, card.complexity, card.comment, card.photo, card.latitude, card.longitude, status_id, card.points, card.city_id, district_id " +
                    "FROM Card " +
                    "INNER JOIN Cleaning ON card.card_id = Cleaning.card_id " +
                    "WHERE card.card_id = :card_id AND is_deleted != true " +
                    "ORDER BY Cleaning.time DESC " +
                    "LIMIT 1")
            .param("card_id", id)
            .query(CardInfo.class)
            .optional().orElseThrow(CardNotFoundException::new);
    }

    @Override
    public List<ShortCardInfo> getListCards(Integer limit, Integer offset) {
        return client.sql(
                "SELECT Card.card_id, Card.complexity, Card.longitude, Card.latitude, m.max_status, Card.city_id, Card.district_id " +
                    "FROM Card INNER JOIN maxstatus m ON Card.card_id = m.card_id " +
                    "WHERE Card.is_deleted != true " +
                    "LIMIT :limit OFFSET :offset"
            )
            .param("limit", limit)
            .param("offset", offset)
            .query(ShortCardInfo.class).list();
    }
}
