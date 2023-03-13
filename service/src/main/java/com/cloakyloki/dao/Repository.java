package com.cloakyloki.dao;

import com.cloakyloki.entity.GenericEntity;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

public interface Repository<S extends Serializable, E extends GenericEntity<S>> {

    E save(E entity);

    void delete(E entity);

    void update(E entity);

    default Optional<E> findById(S id) {
        return findById(id, emptyMap());
    }

    Optional<E> findById(S id, Map<String, Object> properties);
}