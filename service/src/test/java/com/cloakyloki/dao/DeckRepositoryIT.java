package com.cloakyloki.dao;

import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.util.TestDataProvider;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class DeckRepositoryIT extends IntegrationTestBase {

    private final DeckRepository deckRepository = context.getBean(DeckRepository.class);
    private final UserRepository userRepository = context.getBean(UserRepository.class);

    @Test
    void deleteDeck() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        userRepository.save(testUser);
        deckRepository.save(deck);

        deckRepository.delete(deck);

        assertThat(deckRepository.findById(deck.getId())).isEmpty();
    }

    @Test
    void updateDeck() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        userRepository.save(testUser);
        deckRepository.save(deck);

        deck.setFavourite(false);
        deckRepository.update(deck);
        deckRepository.getEntityManager().clear();

        assertThat(deckRepository.findById(deck.getId())).isEqualTo(Optional.of(deck));
    }

    @Test
    void createDeck() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        userRepository.save(testUser);
        deckRepository.save(deck);
        deckRepository.getEntityManager().clear();

        assertThat(deckRepository.findById(deck.getId())).isNotNull();
    }

    @Test
    void findDeckById() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        userRepository.save(testUser);
        deckRepository.save(deck);
        deckRepository.getEntityManager().clear();

        assertThat(deckRepository.findById(deck.getId())).isEqualTo(Optional.of(deck));
    }
}