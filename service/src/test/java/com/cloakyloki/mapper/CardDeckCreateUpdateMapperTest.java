package com.cloakyloki.mapper;

import com.cloakyloki.dto.CardDeckCreateUpdateDto;
import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.CardDeck;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.repository.CardRepository;
import com.cloakyloki.repository.DeckRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CardDeckCreateUpdateMapperTest {

    @Mock
    private CardRepository cardRepository;
    @Mock
    private DeckRepository deckRepository;
    @InjectMocks
    private CardDeckCreateUpdateMapper mapper;

    @Test
    void map() {
        var card = new Card(1L, "testCard", null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                "123", null, null, new ArrayList<CardDeck>(), null);
        var deck = new Deck(1L, "test", null, new ArrayList<CardDeck>(), null, null);
        var expectedResult = new CardDeck(null, null, card, deck, null);
        var cardDeckCreateUpdateDto = new CardDeckCreateUpdateDto("newTest", 10L, 10L);

        doReturn(Optional.of(card)).when(cardRepository).findById(10L);
        doReturn(Optional.of(deck)).when(deckRepository).findById(10L);

        var actualResult = mapper.map(cardDeckCreateUpdateDto);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testMap() {
        var card = new Card(1L, "testCard", null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                "123", null, null, new ArrayList<CardDeck>(), null);
        var deck = new Deck(1L, "test", null, new ArrayList<CardDeck>(), null, null);
        var expectedResult = new CardDeck(null, null, card, deck, null);
        var cardDeckCreateUpdateDto = new CardDeckCreateUpdateDto("newTest", 10L, 10L);

        doReturn(Optional.of(card)).when(cardRepository).findById(10L);
        doReturn(Optional.of(deck)).when(deckRepository).findById(10L);

        var actualResult = mapper.map(cardDeckCreateUpdateDto, expectedResult);

        assertEquals(expectedResult, actualResult);
    }
}