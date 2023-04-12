package com.cloakyloki.repository;

import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.entity.Card;

import java.util.List;

public interface FilterCardRepository {

    List<Card> findAllByFilter(CardFilter filter);
}
