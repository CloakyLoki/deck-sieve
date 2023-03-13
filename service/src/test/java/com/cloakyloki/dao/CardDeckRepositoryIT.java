package com.cloakyloki.dao;

import com.cloakyloki.entity.CardDeck;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.entity.User;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.util.TestDataProvider;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CardDeckRepositoryIT extends IntegrationTestBase {

    CardDeckRepository cardDeckRepository = new CardDeckRepository(CardDeck.class, session);

    @Test
    void deleteCardDeck() {
        var mishraCard = TestDataProvider.createMishraCard();
        var user = TestDataProvider.createTestUser();
        var testDeck = TestDataProvider.createTestDeck(user);
        var testCardDeck = TestDataProvider.createTestCardDeck();
        session.save(mishraCard);
        session.save(user);
        session.save(testDeck);
        cardDeckRepository.save(testCardDeck);

        cardDeckRepository.delete(testCardDeck);

        assertThat(session.get(CardDeck.class, testCardDeck.getId())).isNull();
    }

    @Test
    void updateCardDeck() {
        var mirrorCard = TestDataProvider.createMirrorCard();
        var mishraCard = TestDataProvider.createMishraCard();
        var user = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.USER)
                .isActive(true)
                .build();
        var deck = Deck.builder()
                .name("Artifacts")
                .favourite(true)
                .user(user)
                .build();
        var testCardDeck = CardDeck.builder()
                .card(mirrorCard)
                .deck(deck)
                .build();
        session.save(mirrorCard);
        session.save(mishraCard);
        session.save(user);
        session.save(deck);
        session.save(testCardDeck);

        testCardDeck.setCard(mishraCard);
        cardDeckRepository.update(testCardDeck);
        session.clear();

        assertThat(session.get(CardDeck.class, testCardDeck.getId())).isEqualTo(testCardDeck);
    }

    @Test
    void createCardDeck() {
        var mishraCard = TestDataProvider.createMishraCard();
        var user = TestDataProvider.createTestUser();
        var testDeck = TestDataProvider.createTestDeck(user);
        var testCardDeck = TestDataProvider.createTestCardDeck();
        session.save(mishraCard);
        session.save(user);
        session.save(testDeck);

        cardDeckRepository.save(testCardDeck);
        session.clear();

        assertThat(session.get(CardDeck.class, testCardDeck.getId())).isNotNull();
    }

    @Test
    void findCardDeckById() {
        var mirrorCard = TestDataProvider.createMirrorCard();
        var user = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.USER)
                .isActive(true)
                .build();
        var deck = Deck.builder()
                .name("Artifacts")
                .favourite(true)
                .user(user)
                .build();
        var testCardDeck = CardDeck.builder()
                .card(mirrorCard)
                .deck(deck)
                .build();
        session.save(mirrorCard);
        session.save(user);
        session.save(deck);

        cardDeckRepository.save(testCardDeck);
        session.clear();

        assertThat(cardDeckRepository.findById(testCardDeck.getId())).isEqualTo(Optional.of(testCardDeck));
    }
}