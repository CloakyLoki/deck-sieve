package com.cloakyloki.dao.repository.filter;

import com.cloakyloki.dao.predicate.QPredicate;
import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.entity.Card;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cloakyloki.entity.QCard.card;

@RequiredArgsConstructor
public class FilterCardRepositoryImpl implements FilterCardRepository {

    private final EntityManager entityManager;

    @Override
    public List<Card> findAllByFilter(CardFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getCardName(), card.name::containsIgnoreCase)
                .add(filter.getManaValue(), card.manaValue::eq)
                .add(filter.getText(), card.text::containsIgnoreCase)
                .add(filter.getFlavorText(), card.flavorText::containsIgnoreCase)
                .add(filter.getKeywords(), card.keywords::containsIgnoreCase)
                .buildAnd();

        return new JPAQuery<Card>(entityManager)
                .select(card)
                .from(card)
                .where(predicate)
                .fetch();
    }
}