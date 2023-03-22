package com.cloakyloki.dao;

import com.cloakyloki.entity.CardDeck;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CardDeckRepository extends AbstractRepository<Long, CardDeck> {

    public CardDeckRepository(EntityManager entityManager) {
        super(CardDeck.class, entityManager);
    }
}
