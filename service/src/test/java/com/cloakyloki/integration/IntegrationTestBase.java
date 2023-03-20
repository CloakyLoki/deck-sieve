package com.cloakyloki.integration;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public abstract class IntegrationTestBase {

    protected static Session session;
    protected static AnnotationConfigApplicationContext context;

    @BeforeAll
    static void init() {
        context = new AnnotationConfigApplicationContext("com.cloakyloki.config");
        session = (Session) context.getBean(EntityManager.class);
        var configuration = context.getBean(Configuration.class);
        configuration.configure();
    }

    @BeforeEach
    void getSession() {
        session.beginTransaction();
    }

    @AfterEach
    void closeSession() {
        session.getTransaction().rollback();
    }

    @AfterAll
    static void close() {
        context.close();
    }
}