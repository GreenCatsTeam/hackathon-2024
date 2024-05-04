package com.greencats.repository;

import com.greencats.dto.nearcards.Coordinates;
import java.util.List;

public interface NearCardsRepository {
    List<Long> getNearCards(Coordinates coordinates);
}
