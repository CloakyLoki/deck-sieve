package com.cloakyloki.dao;

import com.cloakyloki.entity.Manacost;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ManacostRepository extends AbstractRepository<Long, Manacost> {

    public ManacostRepository(EntityManager entityManager) {
        super(Manacost.class, entityManager);
    }
}
