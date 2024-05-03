package com.greencats.dto.card;

public record CardInfo(
    Integer complexity,
    String comment,
    String photo,
    Double latitude,
    Double longitude,
    Integer points,
    Integer city_id
) {
}
