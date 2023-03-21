package com.cloakyloki.entity;

import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import com.cloakyloki.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ManacostIT extends IntegrationTestBase {

    @Test
    void addManacost() {
        var card = Card.builder()
                .name("test")
                .type(CardType.LAND)
                .rarity(Rarity.COMMON)
                .text("test text")
                .keywords("test word")
                .isBanned(false)
                .build();
        var expectedManacost = Manacost.builder()
                .card(card)
                .black(1)
                .red(3)
                .blue(1)
                .green(4)
                .common(2)
                .build();
        entityManager.persist(card);
        entityManager.persist(expectedManacost);
        entityManager.clear();

        var actualManacost = entityManager.find(Manacost.class, expectedManacost.getId());

        assertThat(actualManacost.getId()).isNotNull();
    }

    @Test
    void getManacost() {
        var card = Card.builder()
                .name("test")
                .type(CardType.LAND)
                .rarity(Rarity.COMMON)
                .text("test text")
                .keywords("test word")
                .isBanned(false)
                .build();
        var expectedManacost = Manacost.builder()
                .card(card)
                .black(1)
                .red(3)
                .blue(1)
                .white(1)
                .green(4)
                .common(2)
                .build();
        entityManager.persist(card);
        entityManager.persist(expectedManacost);
        entityManager.clear();

        var actualManacost = entityManager.find(Manacost.class, expectedManacost.getId());

        assertEquals(expectedManacost, actualManacost);
    }

    @Test
    void updateManaCost() {
        var card = Card.builder()
                .name("test")
                .type(CardType.LAND)
                .rarity(Rarity.COMMON)
                .text("test text")
                .keywords("test word")
                .isBanned(false)
                .build();
        var expectedManacost = Manacost.builder()
                .card(card)
                .black(1)
                .red(3)
                .blue(1)
                .white(1)
                .green(4)
                .common(2)
                .build();
        entityManager.persist(card);
        entityManager.persist(expectedManacost);
        entityManager.clear();

        expectedManacost.setBlack(null);
        entityManager.merge(expectedManacost);
        entityManager.flush();
        entityManager.clear();

        var actualManacost = entityManager.find(Manacost.class, expectedManacost.getId());

        assertEquals(expectedManacost, actualManacost);
    }

    @Test
    void deleteManacost() {
        var card = Card.builder()
                .name("test")
                .type(CardType.LAND)
                .rarity(Rarity.COMMON)
                .text("test text")
                .keywords("test word")
                .isBanned(false)
                .build();
        var manacost = Manacost.builder()
                .card(card)
                .black(1)
                .red(3)
                .blue(1)
                .white(1)
                .green(4)
                .common(2)
                .build();
        entityManager.persist(card);
        entityManager.persist(manacost);
        entityManager.clear();

        assertThat(manacost.getId()).isNotNull();

        entityManager.remove(manacost);
        entityManager.flush();
        entityManager.clear();

        assertNull(entityManager.find(Manacost.class, manacost.getId()));
    }
}