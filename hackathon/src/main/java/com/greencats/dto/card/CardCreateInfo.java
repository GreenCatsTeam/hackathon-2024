package com.greencats.dto.card;

public record CardCreateInfo(
        Long userId,
        Integer complexity,
        String comment,
        String photo,
        Double longitude,
        Double latitude,
        Long statusId,
        Long cityId,
        Long districtId
) {
}
