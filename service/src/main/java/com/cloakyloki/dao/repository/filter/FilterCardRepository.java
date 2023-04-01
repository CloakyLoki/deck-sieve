package com.cloakyloki.dao.repository.filter;

import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.entity.Card;

import java.util.List;

public interface FilterCardRepository {

    List<Card> findAllByFilter(CardFilter filter);
}