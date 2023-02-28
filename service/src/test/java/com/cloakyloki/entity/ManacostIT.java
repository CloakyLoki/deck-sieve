package com.cloakyloki.entity;

import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ManacostIT extends IntegrationTestBase {

    private Session session;

    @BeforeEach
    void sessionInit() {
        this.session = sessionFactory.openSession();
        this.session.beginTransaction();
    }

    @AfterEach
    void cleanAfterTest() {
        this.session.getTransaction().rollback();
        this.session.close();
    }

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
        session.save(card);
        session.save(expectedManacost);
        session.clear();

        var actualManacost = session.get(Manacost.class, expectedManacost.getId());

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
        session.save(card);
        session.save(expectedManacost);
        session.clear();

        var actualManacost = session.get(Manacost.class, expectedManacost.getId());

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
        session.save(card);
        session.save(expectedManacost);
        session.clear();

        expectedManacost.setBlack(null);
        session.update(expectedManacost);
        session.flush();
        session.clear();

        var actualManacost = session.get(Manacost.class, expectedManacost.getId());

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
        session.save(card);
        session.save(manacost);
        session.clear();

        assertThat(manacost.getId()).isNotNull();

        session.delete(manacost);
        session.flush();
        session.clear();

        assertNull(session.get(Manacost.class, manacost.getId()));
    }
}