package com.cloakyloki.service;

import com.cloakyloki.dto.UserReadDto;
import com.cloakyloki.entity.Deck;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    @Mock
    private Deck deck;

    @InjectMocks
    private UserService userService;

    @Test
    public void findById() {
        Mockito.doReturn(Optional.of(new User(USER_ID, USERNAME, "123", Role.USER, true, List.of(deck), 0L)))
                .when(userRepository).findById(USER_ID);

        var actualUser = userService.findById(USER_ID);

        assertTrue(actualUser.isPresent());

        var expectedUser = new UserReadDto(USER_ID, USERNAME, "123", Role.USER, true);
        actualUser.ifPresent(actual -> Assertions.assertEquals(expectedUser, actualUser));
    }
}
