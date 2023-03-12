package com.cloakyloki.dao;

import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.util.TestDataProvider;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DeckRepositoryIT extends IntegrationTestBase {

    DeckRepository deckRepository = new DeckRepository(Deck.class, session);

    @Test
    void deleteDeck() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        session.save(testUser);
        deckRepository.saveEntity(deck);

        deckRepository.deleteEntity(deck);

        assertThat(session.get(Card.class, deck.getId())).isNull();
    }

    @Test
    void updateDeck() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        session.save(testUser);
        deckRepository.saveEntity(deck);
        session.clear();

        deck.setFavourite(false);
        deckRepository.updateEntity(deck);
        session.clear();

        assertThat(session.get(Deck.class, deck.getId())).isEqualTo(deck);
    }

    @Test
    void createDeck() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        session.save(testUser);
        deckRepository.saveEntity(deck);
        session.clear();

        assertThat(session.get(Deck.class, deck.getId())).isNotNull();
    }

    @Test
    void findDeckById() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        session.save(testUser);
        deckRepository.saveEntity(deck);
        session.clear();

        assertThat(deckRepository.findById(deck.getId())).isEqualTo(Optional.of(deck));
    }
}