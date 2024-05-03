package com.greencats.dto.card;

public record CardInfo(
        Long cardId,
    Long userId,
    Integer complexity,
    String comment,
    String photo,
    Double latitude,
    Double longitude,
    Integer points,
    Long cityId,
        Long districtId
) {
}
