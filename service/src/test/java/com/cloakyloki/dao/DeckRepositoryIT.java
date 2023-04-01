package com.cloakyloki.dao;

import com.cloakyloki.dao.repository.DeckRepository;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.integration.annotation.IT;
import com.cloakyloki.util.TestDataProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
public class DeckRepositoryIT {

    private final DeckRepository deckRepository;
    private final EntityManager entityManager;

    @Test
    void delete() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        entityManager.persist(testUser);
        entityManager.persist(deck);

        deckRepository.delete(deck);
        entityManager.flush();
        entityManager.clear();

        assertThat(entityManager.find(Deck.class, deck.getId())).isNull();
    }

    @Test
    void update() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        entityManager.persist(testUser);
        entityManager.persist(deck);

        deck.setFavourite(false);
        deckRepository.saveAndFlush(deck);
        entityManager.clear();

        assertThat(deckRepository.findById(deck.getId())).isEqualTo(Optional.of(deck));
    }

    @Test
    void create() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        entityManager.persist(testUser);

        deckRepository.save(deck);
        entityManager.clear();

        assertThat(deckRepository.findById(deck.getId())).isNotNull();
    }

    @Test
    void findById() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        entityManager.persist(testUser);
        entityManager.persist(deck);
        entityManager.clear();

        assertThat(deckRepository.findById(deck.getId())).isEqualTo(Optional.of(deck));
    }
}