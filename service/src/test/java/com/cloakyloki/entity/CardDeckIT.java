package com.cloakyloki.entity;

import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import com.cloakyloki.entity.enumerated.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CardDeckIT {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    static void init() {
        var configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @BeforeEach
    void sessionInit() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void cleanAfterTest() {
        session.getTransaction().rollback();
        session.close();
    }

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
        session.save(card);
        session.save(user);
        session.save(deck);
        session.save(cardDeck);
        session.clear();

        var actualCardDeck = session.get(CardDeck.class, cardDeck.getId());

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
        session.save(card);
        session.save(user);
        session.save(deck);
        session.save(expectedCardDeck);
        session.clear();

        var actualCardDeck = session.get(CardDeck.class, expectedCardDeck.getId());

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
        session.save(card);
        session.save(user);
        session.save(deck);
        session.save(expectedCardDeck);
        session.clear();

        expectedCardDeck.setName("newCardDeck");
        session.update(expectedCardDeck);
        session.flush();
        session.clear();

        var actualCardDeck = session.get(CardDeck.class, expectedCardDeck.getId());

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
        session.save(card);
        session.save(user);
        session.save(deck);
        session.save(cardDeck);
        session.clear();

        assertThat(cardDeck.getId()).isNotNull();

        session.delete(cardDeck);
        session.flush();
        session.clear();

        assertNull(session.get(CardDeck.class, cardDeck.getId()));
    }
}