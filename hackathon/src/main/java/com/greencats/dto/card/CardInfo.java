package com.greencats.dto.card;

public record CardInfo(
    Long cardId,
    Long userId,
    Integer complexity,
    String comment,
    String photo,
    Double latitude,
    Double longitude,
    Long statusId,
    Integer points,
    String cityName,
    String districtName
) {
}
