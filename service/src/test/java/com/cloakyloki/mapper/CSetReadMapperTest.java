package com.cloakyloki.mapper;

import com.cloakyloki.dto.CSetReadDto;
import com.cloakyloki.entity.CSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CSetReadMapperTest {

    @InjectMocks
    private CSetReadMapper cSetReadMapper;

    @Test
    void map() {
        var set = new CSet("testCode", null, null, null, null,
                null, null, 0, 0, null, null);
        var expectedResult = new CSetReadDto(set.getCode(), null, null, null, null,
                null, null, false, false, null);

        var actualResult = cSetReadMapper.map(set);

        assertEquals(expectedResult, actualResult);
    }
}