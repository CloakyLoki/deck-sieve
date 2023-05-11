package com.cloakyloki.service;

import com.cloakyloki.dto.CardDeckCreateUpdateDto;
import com.cloakyloki.dto.CardDeckReadDto;
import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.CardDeck;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.mapper.CardDeckCreateUpdateMapper;
import com.cloakyloki.mapper.CardDeckReadMapper;
import com.cloakyloki.repository.CardDeckRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CardDeckServiceTest {

    private static final Long CARDDECK_ID = 1L;
    private static final Long CARD_ID = 2L;
    private static final Long DECK_ID = 3L;

    @Mock
    private CardDeckRepository cardDeckRepository;
    @Mock
    private CardDeckCreateUpdateMapper cardDeckCreateUpdateMapper;
    @Mock
    private CardDeckReadMapper cardDeckReadMapper;
    @InjectMocks
    private CardDeckService cardDeckService;

    @Test
    void create() {
        var card = new Card(CARD_ID, "test", null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        Deck deck = new Deck(DECK_ID, "test", null, null, null, null);
        var cardDeck = new CardDeck(CARDDECK_ID, "test", card, deck, null);
        var cardDeckReadDto = new CardDeckReadDto(
                cardDeck.getCard().getId(),
                cardDeck.getDeck().getId());
        var cardDeckCreateUpdateDto = new CardDeckCreateUpdateDto(
                cardDeck.getName(),
                cardDeck.getCard().getId(),
                cardDeck.getDeck().getId());

        doReturn(cardDeck).when(cardDeckCreateUpdateMapper).map(cardDeckCreateUpdateDto);
        doReturn(cardDeck).when(cardDeckRepository).saveAndFlush(cardDeck);
        doReturn(cardDeckReadDto).when(cardDeckReadMapper).map(cardDeck);

        var actualResult = cardDeckService.create(cardDeckCreateUpdateDto);

        assertEquals(cardDeckReadDto, actualResult);
        assertThrows(NoSuchElementException.class, () -> cardDeckService.create(
                new CardDeckCreateUpdateDto("dummy", null, null)));
    }

    @Test
    void delete() {

        assertThrows(NoSuchElementException.class, () -> cardDeckService.delete(Long.MAX_VALUE, Long.MAX_VALUE));
    }
}