package com.cloakyloki.mapper;

import com.cloakyloki.dto.UserReadDto;
import com.cloakyloki.util.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserReadMapperTest {

    @InjectMocks
    private UserReadMapper userReadMapper;

    @Test
    void map() {
        var user = TestDataProvider.createTestUser();
        var expectedResult = new UserReadDto(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.getIsActive());
        var actualResult = userReadMapper.map(user);

        assertEquals(expectedResult, actualResult);
    }
}