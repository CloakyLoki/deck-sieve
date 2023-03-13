package com.cloakyloki.dao;

import com.cloakyloki.entity.User;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.util.TestDataProvider;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryIT extends IntegrationTestBase {

    UserRepository userRepository = new UserRepository(User.class, session);

    @Test
    void deleteUser() {
        var testUser = TestDataProvider.createTestUser();
        userRepository.save(testUser);

        userRepository.delete(testUser);

        assertThat(session.get(User.class, testUser.getId())).isNull();
    }

    @Test
    void updateUser() {
        var testUser = TestDataProvider.createTestUser();
        userRepository.save(testUser);
        session.clear();

        testUser.setRole(Role.USER);
        userRepository.update(testUser);
        session.clear();

        assertThat(session.get(User.class, testUser.getId())).isEqualTo(testUser);
    }

    @Test
    void createUser() {
        var testUser = TestDataProvider.createTestUser();
        session.save(testUser);

        userRepository.save(testUser);
        session.clear();

        assertThat(session.get(User.class, testUser.getId())).isNotNull();
    }

    @Test
    void findUserById() {
        var testUser = TestDataProvider.createTestUser();
        userRepository.save(testUser);
        session.clear();

        assertThat(userRepository.findById(testUser.getId())).isEqualTo(Optional.of(testUser));
    }
}