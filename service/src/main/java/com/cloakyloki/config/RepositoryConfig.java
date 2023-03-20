package com.cloakyloki.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import java.lang.reflect.Proxy;

@Configuration
@ComponentScan(basePackages = "com.cloakyloki.dao")
public class RepositoryConfig {

    @Bean
    @Autowired
    EntityManager entityManager(org.hibernate.cfg.Configuration configuration) {
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
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
}