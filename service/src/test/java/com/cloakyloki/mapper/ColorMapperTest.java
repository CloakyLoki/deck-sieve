package com.cloakyloki.mapper;

import com.cloakyloki.dto.ManacostDto;
import com.cloakyloki.entity.enumerated.ColorIndicator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ColorMapperTest {

    private static final String MANACOST = "{B}{2}";

    @InjectMocks
    private ColorMapper colorMapper;

    @Test
    void map() {
        var expectedResult = new ManacostDto("2", List.of(ColorIndicator.B));

        var actualResult = colorMapper.map(MANACOST);

        assertEquals(expectedResult, actualResult);
    }
}