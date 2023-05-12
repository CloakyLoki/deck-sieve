package com.cloakyloki.mapper;

import com.cloakyloki.dto.CardDeckReadDto;
import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.CardDeck;
import com.cloakyloki.entity.Deck;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CardDeckReadMapperTest {

    @InjectMocks
    private CardDeckReadMapper cardDeckReadMapper;

    @Test
    void map() {
        var card = new Card(1L, "testCard", null, null, null, null, null, null,
                null, null, null, null, null, null, null, null,
                "123", null, null, null, null);
        var deck = new Deck(1L, "test", null, null, null, null);
        var cardDeck = new CardDeck(1L, "test", card, deck, null);
        var expectedResult = new CardDeckReadDto(cardDeck.getCard().getId(), cardDeck.getDeck().getId());

        var actualResult = cardDeckReadMapper.map(cardDeck);

        assertEquals(expectedResult, actualResult);
    }
}