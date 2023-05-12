package com.cloakyloki.mapper;

import com.cloakyloki.dto.DeckCreateUpdateDto;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.repository.UserRepository;
import com.cloakyloki.util.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DeckCreateUpdateMapperTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DeckCreateUpdateMapper deckCreateUpdateMapper;

    @Test
    void map() {
        var user = TestDataProvider.createTestUser();
        var expectedDeck = new Deck(null, "test", null, null, user, null);
        var deckCreateUpdateDto = new DeckCreateUpdateDto(expectedDeck.getName(), expectedDeck.getUser().getId(), expectedDeck.getFavourite());

        var actualDeck = deckCreateUpdateMapper.map(deckCreateUpdateDto);

        assertEquals(expectedDeck, actualDeck);
    }

    @Test
    void testMap() {
        var user = TestDataProvider.createTestUser();
        var oldDeck = new Deck(null, "dummy", null, null, user, null);
        var expectedDeck = new Deck(null, "test", null, null, user, null);
        var deckCreateUpdateDto = new DeckCreateUpdateDto(expectedDeck.getName(), expectedDeck.getUser().getId(), expectedDeck.getFavourite());

        var actualDeck = deckCreateUpdateMapper.map(deckCreateUpdateDto, oldDeck);

        assertEquals(expectedDeck, actualDeck);
    }
}