package com.cloakyloki.entity;

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

class ManacostTest {

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
    void addManacost() {
        var expectedManacost = Manacost.builder()
                .black(1)
                .red(3)
                .blue(1)
                .white(1)
                .green(4)
                .common(2)
                .build();
        session.save(expectedManacost);
        session.clear();

        var actualManacost = session.get(Manacost.class, expectedManacost.getId());

        assertThat(actualManacost.getId()).isNotNull();
    }

    @Test
    void getManacost() {
        var expectedManacost = Manacost.builder()
                .black(1)
                .red(3)
                .blue(1)
                .white(1)
                .green(4)
                .common(2)
                .build();
        session.save(expectedManacost);
        session.clear();

        var actualManacost = session.get(Manacost.class, expectedManacost.getId());

        assertEquals(expectedManacost, actualManacost);
    }
}