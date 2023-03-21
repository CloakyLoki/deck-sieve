package com.cloakyloki.integration;

import com.cloakyloki.config.RepositoryConfig;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public abstract class IntegrationTestBase {

    protected static EntityManager entityManager;
    protected static AnnotationConfigApplicationContext context;

    @BeforeAll
    static void init() {
        context = new AnnotationConfigApplicationContext(RepositoryConfig.class);
        entityManager = context.getBean(EntityManager.class);
        var configuration = context.getBean(Configuration.class);
        configuration.configure();
    }

    @BeforeEach
    void getEntityManager() {
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void closeSession() {
        entityManager.getTransaction().rollback();
    }
}