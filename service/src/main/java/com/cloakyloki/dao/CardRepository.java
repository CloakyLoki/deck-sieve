package com.cloakyloki.dao;

import com.cloakyloki.dao.predicate.CriteriaPredicate;
import com.cloakyloki.dao.predicate.QPredicate;
import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.Card_;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.graph.GraphSemantic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.List;

import static com.cloakyloki.entity.QCard.card;

@Repository
public class CardRepository extends AbstractRepository<Long, Card> {

    public CardRepository(EntityManager entityManager) {
        super(Card.class, entityManager);
    }

    public List<Card> getCardByName(String cardName) {
        return new JPAQuery<Card>(getEntityManager())
                .select(card)
                .from(card)
                .where(card.name.eq(cardName))
                .fetch();
    }

    public List<Card> getCardByTextOrKeyword(EntityManager entityManager, CardFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getText(), card.text::eq)
                .add(filter.getKeywords(), card.keywords::eq)
                .buildOr();

        return new JPAQuery<Card>(entityManager)
                .select(card)
                .from(card)
                .where(predicate)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), entityManager.getEntityGraph("cardInfoWithManacost"))
                .fetch();
    }

    public List<Card> getCardByManaValueAndType(EntityManager entityManager, CardFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getCardType(), card.type::eq)
                .add(filter.getManaValue(), card.manaValue::eq)
                .buildAnd();

        return new JPAQuery<Card>(entityManager)
                .select(card)
                .from(card)
                .where(predicate)
                .fetch();
    }

    public List<Card> getCardByAnyParameter(EntityManager entityManager, CardFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getCardName(), card.name::eq)
                .add(filter.getManaValue(), card.manaValue::eq)
                .add(filter.getCardType(), card.type::eq)
                .add(filter.getCardSubType(), card.subtype::eq)
                .add(filter.getCardSuperType(), card.supertype::eq)
                .add(filter.getRarity(), card.rarity::eq)
                .add(filter.getText(), card.text::eq)
                .add(filter.getFlavorText(), card.flavorText::eq)
                .add(filter.getKeywords(), card.keywords::eq)
                .add(filter.getPower(), card.power::eq)
                .add(filter.getToughness(), card.toughness::eq)
                .add(filter.getIsBanned(), card.isBanned::eq)
                .buildOr();

        return new JPAQuery<Card>(entityManager)
                .select(card)
                .from(card)
                .where(predicate)
                .fetch();
    }

    public List<Card> getCardBySubtypeAndManavalue(EntityManager entityManager, CardFilter filter) {
        var cb = entityManager.getCriteriaBuilder();
        var criteria = cb.createQuery(Card.class);
        var card = criteria.from(Card.class);

        var predicates = CriteriaPredicate.builder()
                .add(filter.getCardSubType(), it -> cb.equal(card.get(Card_.SUBTYPE), it))
                .add(filter.getManaValue(), it -> cb.equal(card.get(Card_.MANA_VALUE), it))
                .build();

        criteria.select(card)
                .where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }
}