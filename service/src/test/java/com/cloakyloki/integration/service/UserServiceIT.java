package com.cloakyloki.integration.service;

import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.dto.UserReadDto;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.repository.UserRepository;
import com.cloakyloki.service.UserService;
import com.cloakyloki.util.TestDataProvider;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class UserServiceIT extends IntegrationTestBase {

    private final UserService userService;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Test
    void findAll() {
        var testUser = TestDataProvider.createTestUser();
        userRepository.save(testUser);
        entityManager.clear();

        Assertions.assertThat(userService.findAll().size()).isEqualTo(1);
    }

    @Test
    void findById() {
        var expectedUser = TestDataProvider.createTestUser();
        userRepository.save(expectedUser);
        entityManager.clear();

        Optional<UserReadDto> maybeUser = userService.findById(expectedUser.getId());

        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(userReadDto -> assertEquals("CloakyLoki", userReadDto.getUsername()));
    }

    @Test
    void create() {
        UserCreateUpdateDto userCreateUpdateDto = new UserCreateUpdateDto(
                "testNick",
                "123",
                Role.USER,
                true
        );
        var actualResult = userService.create(userCreateUpdateDto);

        assertAll(
                () -> assertEquals(userCreateUpdateDto.getUsername(), actualResult.getUsername()),
                () -> assertEquals(userCreateUpdateDto.getRawPassword(), actualResult.getPassword()),
                () -> assertEquals(userCreateUpdateDto.getRole(), actualResult.getRole()),
                () -> assertEquals(userCreateUpdateDto.getIsActive(), actualResult.getIsActive())
        );
    }

    @Test
    void update() {
        var testUser = TestDataProvider.createTestUser();
        userRepository.save(testUser);
        entityManager.clear();

        UserCreateUpdateDto userCreateUpdateDto = new UserCreateUpdateDto(
                "testNick",
                "123",
                Role.USER,
                true
        );
        var actualUser = userService.update(testUser.getId(), userCreateUpdateDto);
        assertTrue(actualUser.isPresent());

        actualUser.ifPresent(user -> assertAll(
                () -> assertEquals(userCreateUpdateDto.getUsername(), user.getUsername()),
                () -> assertEquals(userCreateUpdateDto.getRawPassword(), user.getPassword()),
                () -> assertEquals(userCreateUpdateDto.getRole(), user.getRole()),
                () -> assertEquals(userCreateUpdateDto.getIsActive(), user.getIsActive()))
        );
    }

    @Test
    void delete() {
        var testUser = TestDataProvider.createTestUser();
        userRepository.save(testUser);
        entityManager.clear();

        assertTrue(userService.delete(testUser.getId()));
        assertFalse(userService.delete(-500L));
    }
}