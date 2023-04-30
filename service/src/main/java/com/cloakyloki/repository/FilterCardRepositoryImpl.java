package com.cloakyloki.repository;

import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.entity.Card;
import com.cloakyloki.repository.predicate.QPredicate;
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
                .add(filter.name(), card.name::containsIgnoreCase)
                .add(filter.manaValue(), card.manaValue::eq)
                .add(filter.manacost(), card.manacost::containsIgnoreCase)
                .add(filter.rarity(), card.rarity::containsIgnoreCase)
                .add(filter.type(), card.type::containsIgnoreCase)
                .add(filter.subtype(), card.subtype::containsIgnoreCase)
                .add(filter.supertype(), card.supertype::containsIgnoreCase)
                .add(filter.text(), card.text::containsIgnoreCase)
                .add(filter.flavorText(), card.flavorText::containsIgnoreCase)
                .add(filter.keywords(), card.keywords::containsIgnoreCase)
                .add(filter.power(), card.power::eq)
                .add(filter.artist(), card.artist::containsIgnoreCase)
                .add(filter.toughness(), card.toughness::eq)
                .add(filter.frameVersion(), card.frameVersion::eq)
                .add(filter.isBanned(), card.isBanned::eq)
                .add(filter.setcode(), card.setcode.code::eq)
                .buildAnd();

        return new JPAQuery<Card>(entityManager)
                .select(card)
                .from(card)
                .where(predicate)
                .fetch();
    }
}