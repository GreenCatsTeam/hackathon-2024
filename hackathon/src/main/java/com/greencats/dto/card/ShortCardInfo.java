package com.greencats.dto.card;

public record ShortCardInfo(
    Long cardId,
        Integer complexity,
        Double longitude,
        Double latitude,
        Long statusId,
        Long cityId,
        Long districtId
) {
}
