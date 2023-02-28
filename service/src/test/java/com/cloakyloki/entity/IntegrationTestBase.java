package com.cloakyloki.entity;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class IntegrationTestBase {

    protected static SessionFactory sessionFactory;

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
}