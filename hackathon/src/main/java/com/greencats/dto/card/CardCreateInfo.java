package com.greencats.dto.card;

public record CardCreateInfo (
    Long userId,
    Integer complexity,
    String comment,
    String photo,
    Double longitude,
    Double latitude,
    Integer points,
    Long cityId,
    Long districtId
) {
}
