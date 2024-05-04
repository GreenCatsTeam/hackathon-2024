package com.greencats.dto.card;

public record CardEditInfo (
    Long cardId,
    Integer complexity,
    Long statusId,
    Long userId
) {

}
