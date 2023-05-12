package com.cloakyloki.mapper;

import com.cloakyloki.dto.CardReadDto;
import com.cloakyloki.entity.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CardReadMapperTest {

    private static final String IMAGE_PATH = "1/2/123.jpg";

    @InjectMocks
    private CardReadMapper cardReadMapper;

    @Test
    void map() {
        var card = new Card(1L, "testCard", null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                "123", null, null, null, null);
        var expectedResult = new CardReadDto(card.getId(), card.getName(), null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, IMAGE_PATH, null,
                null, "No code");

        var actualResult = cardReadMapper.map(card);

        assertEquals(expectedResult, actualResult);
    }
}