package com.cloakyloki.dao;

import com.cloakyloki.entity.CardDeck;

import javax.persistence.EntityManager;

public class CardDeckRepository extends AbstractRepository<Long, CardDeck> {

    public CardDeckRepository(Class<CardDeck> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
