package com.cloakyloki.entity;

import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
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

class ManacostIT {

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
    void createManacost() {
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
    void readManacost() {
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