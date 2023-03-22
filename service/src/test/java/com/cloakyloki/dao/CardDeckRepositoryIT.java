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

    private final CardDeckRepository cardDeckRepository = context.getBean(CardDeckRepository.class);
    private final UserRepository userRepository = context.getBean(UserRepository.class);
    private final CardRepository cardRepository = context.getBean(CardRepository.class);
    private final DeckRepository deckRepository = context.getBean(DeckRepository.class);

    @Test
    void deleteCardDeck() {
        var mishraCard = TestDataProvider.createMishraCard();
        var user = TestDataProvider.createTestUser();
        var testDeck = TestDataProvider.createTestDeck(user);
        var testCardDeck = TestDataProvider.createTestCardDeck();
        cardRepository.save(mishraCard);
        userRepository.save(user);
        deckRepository.save(testDeck);
        cardDeckRepository.save(testCardDeck);

        cardDeckRepository.delete(testCardDeck);

        assertThat(cardDeckRepository.findById(testCardDeck.getId())).isEmpty();
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
        cardRepository.save(mirrorCard);
        cardRepository.save(mishraCard);
        userRepository.save(user);
        deckRepository.save(deck);
        cardDeckRepository.save(testCardDeck);

        testCardDeck.setCard(mishraCard);
        cardDeckRepository.update(testCardDeck);
        cardDeckRepository.getEntityManager().clear();

        assertThat(cardDeckRepository.findById(testCardDeck.getId())).isEqualTo(Optional.of(testCardDeck));
    }

    @Test
    void createCardDeck() {
        var mishraCard = TestDataProvider.createMishraCard();
        var user = TestDataProvider.createTestUser();
        var testDeck = TestDataProvider.createTestDeck(user);
        var testCardDeck = TestDataProvider.createTestCardDeck();
        cardRepository.save(mishraCard);
        userRepository.save(user);
        deckRepository.save(testDeck);

        cardDeckRepository.save(testCardDeck);
        cardDeckRepository.getEntityManager().clear();

        assertThat(cardDeckRepository.findById(testCardDeck.getId())).isNotEmpty();
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
        cardRepository.save(mirrorCard);
        userRepository.save(user);
        deckRepository.save(deck);

        cardDeckRepository.save(testCardDeck);
        cardDeckRepository.getEntityManager().clear();

        assertThat(cardDeckRepository.findById(testCardDeck.getId())).isEqualTo(Optional.of(testCardDeck));
    }
}