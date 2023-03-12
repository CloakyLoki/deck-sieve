package com.cloakyloki.dao;

import com.cloakyloki.entity.GenericEntity;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

public interface Repository<S extends Serializable, E extends GenericEntity<S>> {

    E saveEntity(E entity);

    void deleteEntity(E entity);

    void updateEntity(E entity);

    default Optional<E> findById(S id) {
        return findById(id, emptyMap());
    }
    Optional<E> findById(S id, Map<String, Object> properties);
}
