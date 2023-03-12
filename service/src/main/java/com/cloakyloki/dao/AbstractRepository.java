package com.cloakyloki.dao;

import com.cloakyloki.entity.GenericEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractRepository<S extends Serializable, E extends GenericEntity<S>> implements Repository<S, E> {

    private final Class<E> clazz;
    @Getter
    private final EntityManager entityManager;

    @Override
    public E saveEntity(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void deleteEntity(E entity) {
        entityManager.remove(entity);
        entityManager.flush();
    }

    @Override
    public void updateEntity(E entity) {
        entityManager.merge(entity);
        entityManager.flush();
    }

    @Override
    public Optional<E> findById(S id, Map<String, Object> properties) {
        return Optional.ofNullable(entityManager.find(clazz, id, properties));
    }
}