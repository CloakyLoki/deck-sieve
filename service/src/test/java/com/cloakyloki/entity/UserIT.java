package com.cloakyloki.entity;

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

class UserIT {

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
    void addUser() {
        var expectedUser = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        session.save(expectedUser);
        session.clear();

        var actualUser = session.get(User.class, expectedUser.getId());

        assertThat(actualUser.getId()).isNotNull();
    }

    @Test
    void getUser() {
        var expectedUser = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        session.save(expectedUser);
        session.clear();

        var actualUser = session.getReference(User.class, expectedUser.getId());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void updateUser() {
        var expectedUser = User.builder()
                .nickname("CloakyLoki")
                .role(Role.ADMIN)
                .password("123")
                .isActive(true)
                .build();
        session.save(expectedUser);
        session.clear();

        expectedUser.setRole(Role.USER);
        session.update(expectedUser);
        session.flush();
        session.clear();

        var actualUser = session.get(User.class, expectedUser.getId());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void deleteUser() {
        var expectedUser = User.builder()
                .nickname("CloakyLoki")
                .role(Role.ADMIN)
                .password("123")
                .isActive(true)
                .build();
        session.save(expectedUser);
        session.clear();

        assertThat(session.get(User.class, expectedUser.getId()).getId()).isNotNull();

        session.clear();
        session.delete(expectedUser);
        session.flush();
        session.clear();

        assertNull(session.get(User.class, expectedUser.getId()));
    }
}