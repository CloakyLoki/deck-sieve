package com.cloakyloki.dao;

import com.cloakyloki.dao.predicate.CriteriaPredicate;
import com.cloakyloki.dao.predicate.QPredicate;
import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.Card_;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.List;

import static com.cloakyloki.entity.QCard.card;

public class CardRepository extends AbstractRepository<Long, Card> {

    public CardRepository(Class<Card> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }

    public List<Card> getCardByName(Session session, String cardName) {
        return new JPAQuery<Card>(session)
                .select(card)
                .from(card)
                .where(card.name.eq(cardName))
                .fetch();
    }

    public List<Card> getCardByTextOrKeyword(Session session, CardFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getText(), card.text::eq)
                .add(filter.getKeywords(), card.keywords::eq)
                .buildOr();

        return new JPAQuery<Card>(session)
                .select(card)
                .from(card)
                .where(predicate)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), session.getEntityGraph("cardInfoWithManacost"))
                .fetch();
    }

    public List<Card> getCardByManaValueAndType(Session session, CardFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getCardType(), card.type::eq)
                .add(filter.getManaValue(), card.manaValue::eq)
                .buildAnd();

        return new JPAQuery<Card>(session)
                .select(card)
                .from(card)
                .where(predicate)
                .fetch();
    }

    public List<Card> getCardByAnyParameter(Session session, CardFilter filter) {
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

        return new JPAQuery<Card>(session)
                .select(card)
                .from(card)
                .where(predicate)
                .fetch();
    }

    public List<Card> getCardBySubtypeAndManavalue(Session session, CardFilter filter) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Card.class);
        var card = criteria.from(Card.class);

        var predicates = CriteriaPredicate.builder()
                .add(filter.getCardSubType(), it -> cb.equal(card.get(Card_.SUBTYPE), it))
                .add(filter.getManaValue(), it -> cb.equal(card.get(Card_.MANA_VALUE), it))
                .build();

        criteria.select(card)
                .where(predicates.toArray(Predicate[]::new));

        return session.createQuery(criteria).list();
    }
}
