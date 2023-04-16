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
                .add(filter.getName(), card.name::containsIgnoreCase)
                .add(filter.getManaValue(), card.manaValue::eq)
                .add(filter.getManacost(), card.manacost::containsIgnoreCase)
                .add(filter.getRarity(), card.rarity::containsIgnoreCase)
                .add(filter.getType(), card.type::containsIgnoreCase)
                .add(filter.getSubtype(), card.subtype::containsIgnoreCase)
                .add(filter.getSupertype(), card.supertype::containsIgnoreCase)
                .add(filter.getText(), card.text::containsIgnoreCase)
                .add(filter.getFlavorText(), card.flavorText::containsIgnoreCase)
                .add(filter.getKeywords(), card.keywords::containsIgnoreCase)
                .add(filter.getPower(), card.power::eq)
                .add(filter.getArtist(), card.artist::containsIgnoreCase)
                .add(filter.getToughness(), card.toughness::eq)
                .add(filter.getFrameVersion(), card.frameVersion::eq)
                .add(filter.getIsBanned(), card.isBanned::eq)
                .buildAnd();

        return new JPAQuery<Card>(entityManager)
                .select(card)
                .from(card)
                .where(predicate)
                .fetch();
    }
}
