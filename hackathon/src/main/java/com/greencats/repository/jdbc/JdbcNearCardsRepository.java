package com.greencats.repository.jdbc;

import com.greencats.dto.card.CardInfo;
import com.greencats.dto.nearcards.Coordinates;
import com.greencats.repository.NearCardsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcNearCardsRepository implements NearCardsRepository {

    private final JdbcClient client;

    @Override
    public List<Long> getNearCards(Coordinates coordinates) {
        return client.sql(
                "SELECT card.card_id " +
                    "FROM Card " +
                    "INNER JOIN Cleaning ON card.card_id = Cleaning.card_id " +
                    "INNER JOIN public.city c ON c.city_id = Card.city_id " +
                    "INNER JOIN public.district d ON d.district_id = Card.district_id " +
                    "WHERE is_deleted != true AND longitude > :x1 AND longitude < :x2 AND latitude > :y1 AND latitude < :y2 " +
                    "ORDER BY Cleaning.time DESC")
            .param("x1", coordinates.x1())
            .param("x2", coordinates.x2())
            .param("y1", coordinates.y1())
            .param("y2", coordinates.y2())
            .query(Long.class).list();
    }
}
