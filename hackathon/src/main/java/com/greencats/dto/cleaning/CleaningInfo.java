package com.greencats.dto.cleaning;

import java.sql.Timestamp;

public record CleaningInfo(
    Long cleaningId,
    Long cardId,
    Long statusId,
    Long userId,
    Timestamp time,
    Boolean adminProof,
    Integer countOfProof
) {
}
