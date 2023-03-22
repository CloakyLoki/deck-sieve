package com.cloakyloki.entity;

import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserIT extends IntegrationTestBase {

    @Test
    void addUser() {
        var expectedUser = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        entityManager.persist(expectedUser);
        entityManager.clear();

        var actualUser = entityManager.find(User.class, expectedUser.getId());

        assertThat(actualUser.getId()).isNotNull();
    }

    @Test
    void getUser() {
        var expectedUser = User.builder()
                .nickname("Andrey")
                .password("123")
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        entityManager.persist(expectedUser);
        entityManager.clear();

        var actualUser = entityManager.find(User.class, expectedUser.getId());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void updateUser() {
        var expectedUser = User.builder()
                .nickname("CloakyLoki")
                .role(Role.ADMIN)
                .password("123")
                .isActive(true)
                .build();
        entityManager.persist(expectedUser);
        entityManager.clear();

        expectedUser.setRole(Role.USER);
        entityManager.merge(expectedUser);
        entityManager.flush();
        entityManager.clear();

        var actualUser = entityManager.find(User.class, expectedUser.getId());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void deleteUser() {
        var expectedUser = User.builder()
                .nickname("CloakyLoki")
                .role(Role.ADMIN)
                .password("123")
                .isActive(true)
                .build();
        entityManager.persist(expectedUser);
        entityManager.clear();

        assertThat(entityManager.find(User.class, expectedUser.getId()).getId()).isNotNull();

        entityManager.clear();
        entityManager.remove(expectedUser);
        entityManager.flush();
        entityManager.clear();

        assertNull(entityManager.find(User.class, expectedUser.getId()));
    }
}