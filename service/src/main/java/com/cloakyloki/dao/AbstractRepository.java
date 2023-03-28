package com.cloakyloki.dao;

import com.cloakyloki.entity.GenericEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public abstract class AbstractRepository<S extends Serializable, E extends GenericEntity<S>> implements Repository<S, E> {

    private final Class<E> clazz;
    @Getter
    private final EntityManager entityManager;

    @Override
    @Transactional
    public E save(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(E entity) {
        entityManager.remove(entity);
        entityManager.flush();
    }

    @Override
    @Transactional
    public void update(E entity) {
        entityManager.merge(entity);
        entityManager.flush();
    }

    @Override
    public Optional<E> findById(S id, Map<String, Object> properties) {
        return Optional.ofNullable(entityManager.find(clazz, id, properties));
    }
}