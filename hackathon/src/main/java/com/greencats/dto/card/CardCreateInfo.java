package com.greencats.dto.card;

public record CardCreateInfo (
    Long userId,
    Integer complexity,
    String comment,
    String photo,
    Double longitude,
    Double latitude,
    Integer points,
    String cityName,
    String districtName
) {
}
