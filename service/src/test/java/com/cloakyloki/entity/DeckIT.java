package com.cloakyloki.entity;

import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DeckIT extends IntegrationTestBase {

    @Test
    void addDeck() {
        var user = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        var expectedDeck = Deck.builder()
                .name("ArteFuckts")
                .user(user)
                .favourite(true)
                .build();
        session.save(user);
        session.save(expectedDeck);
        session.clear();

        var actualDeck = session.get(Deck.class, expectedDeck.getId());

        assertThat(actualDeck.getId()).isNotNull();
    }

    @Test
    void getDeck() {
        var user = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        var expectedDeck = Deck.builder()
                .name("ArteFuckts")
                .user(user)
                .favourite(true)
                .build();
        session.save(user);
        session.save(expectedDeck);
        session.clear();

        var actualDeck = session.get(Deck.class, expectedDeck.getId());

        assertEquals(expectedDeck, actualDeck);
    }

    @Test
    void updateDeck() {
        var user = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        var expectedDeck = Deck.builder()
                .name("ArteFuckts")
                .user(user)
                .favourite(true)
                .build();
        session.save(user);
        session.save(expectedDeck);
        session.clear();

        expectedDeck.setFavourite(false);
        session.update(expectedDeck);
        session.flush();
        session.clear();

        var actualDeck = session.get(Deck.class, expectedDeck.getId());

        assertEquals(expectedDeck, actualDeck);
    }

    @Test
    void deleteDeck() {
        var user = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        var newDeck = Deck.builder()
                .name("ArteFuckts")
                .user(user)
                .favourite(true)
                .build();
        session.save(user);
        session.save(newDeck);
        session.clear();

        assertThat(newDeck.getId()).isNotNull();

        session.delete(newDeck);
        session.flush();
        session.clear();

        assertNull(session.get(Deck.class, newDeck.getId()));
    }
}