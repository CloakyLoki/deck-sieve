package com.cloakyloki.integration.repository;

import com.cloakyloki.entity.CardDeck;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.entity.User;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.repository.CardDeckRepository;
import com.cloakyloki.util.TestDataProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class CardDeckRepositoryIT extends IntegrationTestBase {

    private final CardDeckRepository cardDeckRepository;
    private final EntityManager entityManager;

    @Test
    void delete() {
        var mishraCard = TestDataProvider.createMishraCard();
        var user = TestDataProvider.createTestUser();
        var testDeck = TestDataProvider.createTestDeck(user);
        var testCardDeck = CardDeck.builder()
                .name("testname")
                .deck(testDeck)
                .card(mishraCard)
                .build();
        entityManager.persist(mishraCard);
        entityManager.persist(user);
        entityManager.persist(testDeck);
        entityManager.persist(testCardDeck);

        cardDeckRepository.delete(testCardDeck);
        entityManager.flush();
        entityManager.clear();

        assertThat(cardDeckRepository.findById(testCardDeck.getId())).isEmpty();
    }

    @Test
    void update() {
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
        entityManager.persist(mirrorCard);
        entityManager.persist(mishraCard);
        entityManager.persist(user);
        entityManager.persist(deck);
        entityManager.persist(testCardDeck);

        testCardDeck.setCard(mishraCard);
        cardDeckRepository.saveAndFlush(testCardDeck);
        entityManager.clear();

        assertThat(cardDeckRepository.findById(testCardDeck.getId())).isEqualTo(Optional.of(testCardDeck));
    }

    @Test
    void create() {
        var mishraCard = TestDataProvider.createMishraCard();
        var user = TestDataProvider.createTestUser();
        var testDeck = TestDataProvider.createTestDeck(user);
        var testCardDeck =
                CardDeck.builder()
                        .name("testname")
                        .card(mishraCard)
                        .deck(testDeck)
                        .build();
        entityManager.persist(mishraCard);
        entityManager.persist(user);
        entityManager.persist(testDeck);

        cardDeckRepository.save(testCardDeck);
        entityManager.clear();

        assertThat(cardDeckRepository.findById(testCardDeck.getId())).isNotEmpty();
    }

    @Test
    void findById() {
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
        entityManager.persist(mirrorCard);
        entityManager.persist(user);
        entityManager.persist(deck);
        entityManager.persist(testCardDeck);
        entityManager.clear();

        assertThat(cardDeckRepository.findById(testCardDeck.getId())).isEqualTo(Optional.of(testCardDeck));
    }
}