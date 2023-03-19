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

    @Bean(name = "entityManager")
    EntityManager entityManager() {
        var configuration = new org.hibernate.cfg.Configuration();
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args) -> method.invoke(sessionFactory.getCurrentSession(), args));
    }

    @Bean(name = "config")
    org.hibernate.cfg.Configuration configuration() {
        return new org.hibernate.cfg.Configuration();
    }

    @Bean
    @Autowired
    SessionFactory sessionFactory(org.hibernate.cfg.Configuration configuration) {
        configuration().configure();
        return configuration.buildSessionFactory();
    }
}