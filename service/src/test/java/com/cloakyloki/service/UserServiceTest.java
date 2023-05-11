package com.cloakyloki.service;

import com.cloakyloki.dto.CustomUser;
import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.dto.UserReadDto;
import com.cloakyloki.entity.User;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.mapper.UserCreateUpdateMapper;
import com.cloakyloki.mapper.UserReadMapper;
import com.cloakyloki.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final Long USER_ID = 1L;
    private static final String USERNAME = "Test";

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserReadMapper userReadMapper;
    @Mock
    private UserCreateUpdateMapper userCreateUpdateMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void findAll() {
        var user = new User(USER_ID, USERNAME, null, null, null, Collections.emptyList(), null);
        var expectedResult = new UserReadDto(USER_ID, USERNAME, null, null, null);

        doReturn(expectedResult).when(userReadMapper).map(user);
        doReturn(List.of(user)).when(userRepository).findAll();

        var actualResultsList = userService.findAll();

        assertEquals(List.of(expectedResult), actualResultsList);
    }

    @Test
    public void findById() {
        var user = new User(USER_ID, USERNAME, null, null, null, null, null);

        doReturn(Optional.of(user))
                .when(userRepository).findById(USER_ID);
        doReturn(new UserReadDto(USER_ID, USERNAME, null, null, null)).when(userReadMapper)
                .map(user);

        var actualUser = userService.findById(USER_ID);

        assertTrue(actualUser.isPresent());

        var expectedUser = new UserReadDto(USER_ID, USERNAME, null, null, null);
        actualUser.ifPresent(actual -> Assertions.assertEquals(Optional.of(expectedUser), actualUser));
    }

    @Test
    public void findByUsername() {
        var user = new User(USER_ID, USERNAME, null, null, null, null, null);

        doReturn(Optional.of(user))
                .when(userRepository).findByUsername(USERNAME);
        doReturn(new UserReadDto(USER_ID, USERNAME, null, null, null)).when(userReadMapper)
                .map(user);

        var actualUser = userService.findByUsername(USERNAME);

        assertTrue(actualUser.isPresent());

        var expectedUser = new UserReadDto(USER_ID, USERNAME, null, null, null);
        actualUser.ifPresent(actual -> Assertions.assertEquals(Optional.of(expectedUser), actualUser));
    }

    @Test
    public void create() {
        var user = new User(USER_ID, USERNAME, "123", null, null, null, null);
        var userCreateUpdateDto = new UserCreateUpdateDto(USERNAME, "123", null, null);
        doReturn(user)
                .when(userCreateUpdateMapper).map(userCreateUpdateDto);
        doReturn(user)
                .when(userRepository).saveAndFlush(user);
        doReturn(new UserReadDto(USER_ID, USERNAME, "123", null, null))
                .when(userReadMapper).map(user);

        var actualUser = userService.create(userCreateUpdateDto);
        var expectedUser = new UserReadDto(USER_ID, USERNAME, "123", null, null);

        assertEquals(expectedUser, actualUser);
        assertThrows(NoSuchElementException.class,
                () -> userService.create(new UserCreateUpdateDto("notExistingUser", "123", null, null)));
    }

    @Test
    public void update() {
        var user = new User(USER_ID, USERNAME, "123", null, null, null, null);
        var updatedUser = new User(USER_ID, USERNAME, "222", null, null, null, null);
        var userUpdateDto = new UserCreateUpdateDto(USERNAME, "222", null, null);
        var expectedUser = new UserReadDto(USER_ID, USERNAME, updatedUser.getPassword(), null, null);

        doReturn(Optional.of(user)).when(userRepository).findById(USER_ID);
        doReturn(updatedUser).when(userCreateUpdateMapper).map(userUpdateDto, user);
        doReturn(updatedUser).when(userRepository).saveAndFlush(updatedUser);
        doReturn(expectedUser).when(userReadMapper).map(updatedUser);

        var actualUser = userService.update(USER_ID, userUpdateDto);

        assertEquals(Optional.of(expectedUser), actualUser);
    }

    @Test
    public void delete() {
        var user = new User(USER_ID, USERNAME, "123", null, null, null, null);

        doReturn(Optional.of(user)).when(userRepository).findById(USER_ID);

        assertTrue(userService.delete(USER_ID));
        assertFalse(userService.delete(Long.MAX_VALUE));
    }

    @Test
    public void loadUserByUsername() {
        var user = new User(USER_ID, USERNAME, "123", Role.USER, null, null, null);
        var expectedREsult = new CustomUser(USER_ID, USERNAME, user.getPassword(), Collections.singleton(Role.USER));

        doReturn(Optional.of(user)).when(userRepository).findByUsername(USERNAME);

        var actualResult = userService.loadUserByUsername(USERNAME);

        assertEquals(expectedREsult, actualResult);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("notUser"));
    }
}