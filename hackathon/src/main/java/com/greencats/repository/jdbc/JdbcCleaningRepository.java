package com.greencats.repository.jdbc;

import com.greencats.dto.cleaning.CleaningCreateInfo;
import com.greencats.dto.cleaning.CleaningInfo;
import com.greencats.exception.CardNotCreatedException;
import com.greencats.exception.CardNotFoundException;
import com.greencats.exception.CityNotFoundException;
import com.greencats.exception.CleaningNotFoundException;
import com.greencats.repository.CleaningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

@Repository
@RequiredArgsConstructor
public class JdbcCleaningRepository implements CleaningRepository {
    private final JdbcClient client;

    @Override
    public Long createCleaning(CleaningCreateInfo cleaningCreateInfo) {
        CleaningInfo cleaningInfo = client.sql (
                "SELECT cleaning_id, card_id, status_id, user_id, time, admin_proof, count_of_proof FROM cleaning " +
                    "WHERE card_id = :card_id ORDER BY time DESC LIMIT 1")
            .param("card_id", cleaningCreateInfo.cardId())
            .query(CleaningInfo.class)
            .optional()
            .orElseThrow(CardNotFoundException::new);

        Long cleaningId = client.sql(
                "INSERT INTO Cleaning(card_id, user_id, status_id, time, admin_proof, count_of_proof)" +
                    "VALUES(:card_id, :user_id, :status_id, :time, :admin_proof, :count_of_proof) RETURNING cleaning_id")
            .param("card_id", cleaningInfo.cardId())
            .param("user_id", cleaningCreateInfo.userId())
            .param("status_id", cleaningInfo.statusId())
            .param("time", Timestamp.from(ZonedDateTime.now().toInstant()))
            .param("admin_proof", cleaningInfo.adminProof())
            .param("count_of_proof", cleaningInfo.countOfProof())
            .query(Long.class)
            .optional().orElseThrow(CardNotFoundException::new);

        return cleaningId;
    }
}



