package com.cloakyloki.dao;

import com.cloakyloki.entity.User;

import javax.persistence.EntityManager;

public class UserRepository extends AbstractRepository<Long, User> {

    public UserRepository(Class<User> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}