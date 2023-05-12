package com.cloakyloki.mapper;

import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserCreateUpdateMapperTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    UserCreateUpdateMapper userCreateUpdateMapper;

    @Test
    void map() {
        var expectedUser = new User(null, "test", null, null, null, null, null);
        var oldUser = new User(null, "test2", "", null, null, null, null);
        var userCreateUpdateDto = new UserCreateUpdateDto(expectedUser.getUsername(), expectedUser.getPassword(), expectedUser.getRole(), expectedUser.getIsActive());
        var actualUser = userCreateUpdateMapper.map(userCreateUpdateDto);
        userCreateUpdateMapper.map(userCreateUpdateDto, oldUser);

        assertEquals(expectedUser, actualUser);
        assertEquals(expectedUser, actualUser);
    }
}