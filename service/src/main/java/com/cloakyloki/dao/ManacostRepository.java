package com.cloakyloki.dao;

import com.cloakyloki.entity.Manacost;

import javax.persistence.EntityManager;

public class ManacostRepository extends AbstractRepository<Long, Manacost> {

    public ManacostRepository(Class<Manacost> clazz, EntityManager entityManager) {
        super(clazz, entityManager);
    }
}
