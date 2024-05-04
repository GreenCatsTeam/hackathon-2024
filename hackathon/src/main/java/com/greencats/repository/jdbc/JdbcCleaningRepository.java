package com.greencats.repository.jdbc;

import com.greencats.dto.cleaning.CleaningCreateInfo;
import com.greencats.exception.CardNotCreatedException;
import com.greencats.exception.CityNotFoundException;
import com.greencats.repository.CleaningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcCleaningRepository implements CleaningRepository {
    private final JdbcClient client;

    @Override
    public Long createCleaning(CleaningCreateInfo cleaningCreateInfo) {
        // input card_id, user_id
        // get status_id, timestamptz

        Long statusId = client.sql("SELECT MaxStatus.max_status FROM MaxStatus WHERE card_id = :card_id")
            .param("card_id", cleaningCreateInfo.cardId())
            .query(Long.class)
            .optional().orElseThrow(CityNotFoundException::new);

        Long cleaningId = client.sql(
                "INSERT INTO Cleaning(card_id, user_id, status_id)" +
                    "VALUES(:card_id, :user_id, :status_id) RETURNING cleaning_id")
            .param("card_id", cleaningCreateInfo.cardId())
            .param("user_id", cleaningCreateInfo.userId())
            .param("status_id", statusId)
            .query(Long.class)
            .optional().orElseThrow(CardNotCreatedException::new);

        return cleaningId;
    }
}


//CREATE TABLE Cleaning (
//    cleaning_id BIGSERIAL PRIMARY KEY,
//    card_id INT REFERENCES Card(card_id) NOT NULL,
//      status_id INT REFERENCES Status(status_id),
//      user_id INT REFERENCES Users(user_id) NULL,
//      time timestamptz
//);


//CREATE TABLE Card (
//    card_id BIGSERIAL PRIMARY KEY,
//    complexity INT ,
//    comment VARCHAR(255),
//photo TEXT,
//latitude DOUBLE PRECISION,
//longitude DOUBLE PRECISION,
//points INT,
//city_id BIGINT REFERENCES City(city_id),
//district_id BIGINT REFERENCES District(district_id),
//is_deleted BOOLEAN DEFAULT FALSE
//);

//CREATE TABLE MaxStatus(
//    card_id INT REFERENCES Card(card_id),
//max_status BIGINT
//);

//CREATE TABLE Users (
//    user_id BIGSERIAL PRIMARY KEY,
//    first_name VARCHAR(100) NOT NULL,
//last_name VARCHAR(100) NOT NULL,
//email VARCHAR(255) NOT NULL,
//password VARCHAR(100) NOT NULL,
//role VARCHAR(20) NOT NULL,
//organization VARCHAR(255) NULL,
//city_id BIGINT REFERENCES City(city_id),
//district_id BIGINT REFERENCES District(district_id),
//is_banned BOOLEAN DEFAULT FALSE,
//is_deleted BOOLEAN DEFAULT FALSE
//);
