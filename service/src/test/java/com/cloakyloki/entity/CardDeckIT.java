package com.cloakyloki.entity;

import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CardDeckIT extends IntegrationTestBase {

    @Test
    void addCardDeck() {
        var card = Card.builder()
                .name("Mishra Eminent One")
                .type(CardType.CREATURE)
                .rarity(Rarity.SPECIAL)
                .text("Nice commander ;)")
                .keywords("commander")
                .isBanned(false)
                .build();
        var user = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        var deck = Deck.builder()
                .name("ArteFuckts")
                .user(user)
                .favourite(true)
                .build();
        var cardDeck = CardDeck.builder()
                .card(card)
                .deck(deck)
                .build();
        entityManager.persist(card);
        entityManager.persist(user);
        entityManager.persist(deck);
        entityManager.persist(cardDeck);
        entityManager.flush();
        entityManager.clear();

        var actualCardDeck = entityManager.find(CardDeck.class, cardDeck.getId());

        assertThat(actualCardDeck).isNotNull();
    }

    @Test
    void getCardDeck() {
        var card = Card.builder()
                .name("Mishra Eminent One")
                .type(CardType.CREATURE)
                .rarity(Rarity.SPECIAL)
                .text("Nice commander ;)")
                .keywords("commander")
                .isBanned(false)
                .build();
        var user = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        var deck = Deck.builder()
                .name("ArteFuckts")
                .user(user)
                .favourite(true)
                .build();
        var expectedCardDeck = CardDeck.builder()
                .card(card)
                .deck(deck)
                .build();
        entityManager.persist(card);
        entityManager.persist(user);
        entityManager.persist(deck);
        entityManager.persist(expectedCardDeck);
        entityManager.clear();

        var actualCardDeck = entityManager.find(CardDeck.class, expectedCardDeck.getId());

        assertEquals(expectedCardDeck, actualCardDeck);
    }

    @Test
    void updateCardDeck() {
        var card = Card.builder()
                .name("Mishra Eminent One")
                .type(CardType.CREATURE)
                .rarity(Rarity.SPECIAL)
                .text("Nice commander ;)")
                .keywords("commander")
                .isBanned(false)
                .build();
        var user = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        var deck = Deck.builder()
                .name("ArteFuckts")
                .user(user)
                .favourite(true)
                .build();
        var expectedCardDeck = CardDeck.builder()
                .name("oldCardDeck")
                .card(card)
                .deck(deck)
                .build();
        entityManager.persist(card);
        entityManager.persist(user);
        entityManager.persist(deck);
        entityManager.persist(expectedCardDeck);
        entityManager.clear();

        expectedCardDeck.setName("newCardDeck");
        entityManager.merge(expectedCardDeck);
        entityManager.flush();
        entityManager.clear();

        var actualCardDeck = entityManager.find(CardDeck.class, expectedCardDeck.getId());

        assertEquals(expectedCardDeck, actualCardDeck);
    }

    @Test
    void deleteCardDeck() {
        var card = Card.builder()
                .name("Mishra Eminent One")
                .type(CardType.CREATURE)
                .rarity(Rarity.SPECIAL)
                .text("Nice commander ;)")
                .keywords("commander")
                .isBanned(false)
                .build();
        var user = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        var deck = Deck.builder()
                .name("ArteFuckts")
                .user(user)
                .favourite(true)
                .build();
        var cardDeck = CardDeck.builder()
                .card(card)
                .deck(deck)
                .build();
        entityManager.persist(card);
        entityManager.persist(user);
        entityManager.persist(deck);
        entityManager.persist(cardDeck);
        entityManager.clear();

        assertThat(cardDeck.getId()).isNotNull();

        entityManager.remove(cardDeck);
        entityManager.flush();
        entityManager.clear();

        assertNull(entityManager.find(CardDeck.class, cardDeck.getId()));
    }
}