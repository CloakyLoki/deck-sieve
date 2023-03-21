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
        entityManager.persist(user);
        entityManager.persist(expectedDeck);
        entityManager.clear();

        var actualDeck = entityManager.find(Deck.class, expectedDeck.getId());

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
        entityManager.persist(user);
        entityManager.persist(expectedDeck);
        entityManager.clear();

        var actualDeck = entityManager.find(Deck.class, expectedDeck.getId());

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
        entityManager.persist(user);
        entityManager.persist(expectedDeck);
        entityManager.clear();

        expectedDeck.setFavourite(false);
        entityManager.merge(expectedDeck);
        entityManager.flush();
        entityManager.clear();

        var actualDeck = entityManager.find(Deck.class, expectedDeck.getId());

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
        entityManager.persist(user);
        entityManager.persist(newDeck);
        entityManager.clear();

        assertThat(newDeck.getId()).isNotNull();

        entityManager.remove(newDeck);
        entityManager.flush();
        entityManager.clear();

        assertNull(entityManager.find(Deck.class, newDeck.getId()));
    }
}