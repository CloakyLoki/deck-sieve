package com.cloakyloki.dao;

import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.integration.annotation.IT;
import com.cloakyloki.util.TestDataProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
class UserRepositoryIT {

    private final UserRepository userRepository;

    @Test
    void deleteUser() {
        var testUser = TestDataProvider.createTestUser();
        userRepository.save(testUser);

        userRepository.delete(testUser);

        assertThat(userRepository.findById(testUser.getId())).isEmpty();
    }

    @Test
    void updateUser() {
        var testUser = TestDataProvider.createTestUser();
        userRepository.save(testUser);
        userRepository.getEntityManager().clear();

        testUser.setRole(Role.USER);
        userRepository.update(testUser);
        userRepository.getEntityManager().clear();

        assertThat(userRepository.findById(testUser.getId())).isEqualTo(Optional.of(testUser));
    }

    @Test
    void createUser() {
        var testUser = TestDataProvider.createTestUser();
        userRepository.save(testUser);
        userRepository.getEntityManager().clear();

        assertThat(userRepository.findById(testUser.getId())).isNotEmpty();
    }

    @Test
    void findUserById() {
        var testUser = TestDataProvider.createTestUser();
        userRepository.save(testUser);
        userRepository.getEntityManager().clear();

        assertThat(userRepository.findById(testUser.getId())).isEqualTo(Optional.of(testUser));
    }
}