package com.cloakyloki.dao;

import com.cloakyloki.entity.Deck;

import javax.persistence.EntityManager;

public class DeckRepository extends AbstractRepository<Long, Deck> {

    public DeckRepository(Class<Deck> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}