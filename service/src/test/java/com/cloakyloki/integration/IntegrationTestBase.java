package com.cloakyloki.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class IntegrationTestBase {

    private static SessionFactory sessionFactory;
    protected Session session;

    @BeforeAll
    static void init() {
        var configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @BeforeEach
    void getSession() {
        session = sessionFactory.getCurrentSession();
//        var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
//                (proxy, method, args) -> method.invoke(sessionFactory.openSession(), args));
        this.session.beginTransaction();
    }

    @AfterEach
    void closeSession() {
        session.getTransaction().rollback();
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }
}