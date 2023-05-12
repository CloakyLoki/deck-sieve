package com.cloakyloki.mapper;

import com.cloakyloki.dto.CardCreateUpdateDto;
import com.cloakyloki.entity.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CardCreateUpdateMapperTest {

    @InjectMocks
    CardCreateUpdateMapper mapper;

    @Test
    void map() {
        var expectedResult = new Card(null, "testCard", null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null);
        var createDto = new CardCreateUpdateDto(expectedResult.getName(), null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null,
                null, "No code");

        var actualResult = mapper.map(createDto);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testMap() {
        var expectedResult = new Card(null, "testCard", null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null);
        var newCard = new Card(null, "newCard", null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                null, null, null, null, null);
        var createDto = new CardCreateUpdateDto(expectedResult.getName(), null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null,
                null, "No code");

        var actualResult = mapper.map(createDto, newCard);

        assertEquals(expectedResult, actualResult);
    }
}