package com.cloakyloki.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import java.lang.reflect.Proxy;

@Configuration
@ComponentScan(basePackages = "com.cloakyloki.dao")
public class RepositoryConfig {

    @Bean
    EntityManager entityManager(SessionFactory sessionFactory) {
        return (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{EntityManager.class},
                (proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args));
    }

    @Bean
    org.hibernate.cfg.Configuration configuration() {
        return new org.hibernate.cfg.Configuration();
    }

    @Bean
    SessionFactory sessionFactory(org.hibernate.cfg.Configuration configuration) {
        configuration().configure();
        return configuration.buildSessionFactory();
    }

    @PreDestroy
    void destroy() {
        System.out.println("Destroy");
        sessionFactory(configuration()).close();
    }
}