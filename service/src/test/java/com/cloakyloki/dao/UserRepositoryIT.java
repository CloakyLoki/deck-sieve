package com.cloakyloki.dao;

import com.cloakyloki.dao.repository.UserRepository;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.integration.annotation.IT;
import com.cloakyloki.util.TestDataProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
class UserRepositoryIT {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Test
    void deleteUser() {
        var testUser = TestDataProvider.createTestUser();
        entityManager.persist(testUser);

        userRepository.delete(testUser);
        entityManager.flush();
        entityManager.clear();

        assertThat(userRepository.findById(testUser.getId())).isEmpty();
    }

    @Test
    void updateUser() {
        var testUser = TestDataProvider.createTestUser();
        entityManager.persist(testUser);
        entityManager.clear();

        testUser.setRole(Role.USER);
        userRepository.saveAndFlush(testUser);
        entityManager.clear();

        assertThat(userRepository.findById(testUser.getId())).isEqualTo(Optional.of(testUser));
    }

    @Test
    void createUser() {
        var testUser = TestDataProvider.createTestUser();

        userRepository.save(testUser);
        entityManager.clear();

        assertThat(userRepository.findById(testUser.getId())).isNotEmpty();
    }

    @Test
    void findUserById() {
        var testUser = TestDataProvider.createTestUser();
        entityManager.persist(testUser);
        entityManager.clear();

        assertThat(userRepository.findById(testUser.getId())).isEqualTo(Optional.of(testUser));
    }
}