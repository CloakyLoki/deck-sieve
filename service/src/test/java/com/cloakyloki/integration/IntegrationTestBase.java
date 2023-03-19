package com.cloakyloki.integration;

import com.cloakyloki.config.RepositoryConfig;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RequiredArgsConstructor
public abstract class IntegrationTestBase {

    private static SessionFactory sessionFactory;
    protected static Session session;
    protected static AnnotationConfigApplicationContext context;

    @BeforeAll
    static void init() {
        context = new AnnotationConfigApplicationContext();
        context.register(RepositoryConfig.class);
        context.refresh();
        session = (Session) context.getBean("entityManager");
        sessionFactory = context.getBean(SessionFactory.class);
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
        sessionFactory.close();
    }
}