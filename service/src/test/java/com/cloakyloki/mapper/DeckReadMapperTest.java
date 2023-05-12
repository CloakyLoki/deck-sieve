package com.cloakyloki.mapper;

import com.cloakyloki.dto.DeckReadDto;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.util.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DeckReadMapperTest {

    @InjectMocks
    private DeckReadMapper deckReadMapper;

    @Test
    void map() {
        var user = TestDataProvider.createTestUser();
        var deck = new Deck(1L, "test", null, null, user, null);
        var expectedResult = new DeckReadDto(deck.getId(), deck.getName(), null, deck.getUser().getId());
        var actualResult = deckReadMapper.map(deck);

        assertEquals(expectedResult, actualResult);
    }
}