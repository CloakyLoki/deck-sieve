package com.cloakyloki.dao;

import com.cloakyloki.entity.Deck;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class DeckRepository extends AbstractRepository<Long, Deck> {

    public DeckRepository(EntityManager entityManager) {
        super(Deck.class, entityManager);
    }
}