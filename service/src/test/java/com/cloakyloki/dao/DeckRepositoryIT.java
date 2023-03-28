package com.cloakyloki.dao;

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
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Test
    void deleteDeck() {
        var testUser = TestDataProvider.createTestUser();
        var deck = TestDataProvider.createTestDeck(testUser);
        userRepository.save(testUser);
        deckRepository.save(deck);

        deckRepository.delete(deck);

        assertThat(entityManager.find(Deck.class, deck.getId())).isNull();
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