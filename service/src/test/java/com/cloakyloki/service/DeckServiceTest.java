package com.cloakyloki.service;

import com.cloakyloki.dto.CardReadDto;
import com.cloakyloki.dto.DeckCreateUpdateDto;
import com.cloakyloki.dto.DeckReadDto;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.entity.enumerated.ColorIndicator;
import com.cloakyloki.mapper.DeckCreateUpdateMapper;
import com.cloakyloki.mapper.DeckReadMapper;
import com.cloakyloki.repository.DeckRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class DeckServiceTest {

    private static final Long DECK_ID = 1L;
    private static final String DECK_NAME = "testdeck";

    @Mock
    private DeckRepository deckRepository;
    @Mock
    private DeckReadMapper deckReadMapper;
    @Mock
    private DeckCreateUpdateMapper deckCreateUpdateMapper;
    @Mock
    private CardService cardService;
    @InjectMocks
    private DeckService deckService;

    @Test
    void findAllByUserId() {
        Deck deck = new Deck(DECK_ID, "test", null, null, null, null);
        DeckReadDto deckReadDto = new DeckReadDto(DECK_ID, deck.getName(), null, null);
        doReturn(List.of(deck)).when(deckRepository).findAllByUserId(DECK_ID);
        doReturn(deckReadDto).when(deckReadMapper).map(deck);

        var expectedResult = List.of(deckReadDto);
        var actualResult = deckService.findAllByUserId(DECK_ID);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void findById() {
        Deck deck = new Deck(DECK_ID, "test", null, null, null, null);
        DeckReadDto expectedResult = new DeckReadDto(DECK_ID, deck.getName(), null, null);

        doReturn(Optional.of(deck)).when(deckRepository).findById(DECK_ID);
        doReturn(expectedResult).when(deckReadMapper).map(deck);

        var actualResult = deckService.findById(DECK_ID);

        Assertions.assertEquals(Optional.of(expectedResult), actualResult);
    }

    @Test
    void delete() {
        Deck deck = new Deck(DECK_ID, "test", null, null, null, null);
        doReturn(Optional.of(deck)).when(deckRepository).findById(DECK_ID);

        assertTrue(deckService.delete(DECK_ID));
        assertFalse(deckService.delete(Long.MAX_VALUE));
    }

    @Test
    void create() {
        Deck deck = new Deck(DECK_ID, "test", null, null, null, null);
        var deckCreateUpdateDto = new DeckCreateUpdateDto(DECK_NAME, null, null);
        var expectedResult = new DeckReadDto(DECK_ID, DECK_NAME, null, null);

        doReturn(deck).when(deckCreateUpdateMapper).map(deckCreateUpdateDto);
        doReturn(deck).when(deckRepository).saveAndFlush(deck);
        doReturn(expectedResult).when(deckReadMapper).map(deck);

        var actualResult = deckService.create(deckCreateUpdateDto);

        assertEquals(expectedResult, actualResult);
        assertThrows(NoSuchElementException.class,
                () -> deckService.create(new DeckCreateUpdateDto("notExistingDeck", Long.MAX_VALUE, null)));
    }

    @Test
    void update() {
        Deck deck = new Deck(DECK_ID, "test", null, null, null, null);
        var updatedDeck = new Deck(DECK_ID, "updatedDeck", null, null, null, null);
        var updateDto = new DeckCreateUpdateDto(updatedDeck.getName(), null, null);
        var expectedResult = new DeckReadDto(updatedDeck.getId(), updatedDeck.getName(), null, null);

        doReturn(Optional.of(deck)).when(deckRepository).findById(DECK_ID);
        doReturn(updatedDeck).when(deckCreateUpdateMapper).map(updateDto, deck);
        doReturn(updatedDeck).when(deckRepository).saveAndFlush(updatedDeck);
        doReturn(expectedResult).when(deckReadMapper).map(updatedDeck);

        var actualResult = deckService.update(DECK_ID, updateDto);

        assertEquals(Optional.of(expectedResult), actualResult);
    }

    @Test
    void getDeckManaProduction() {
        var cardReadDto = new CardReadDto(1L, "test", null, null, null,
                null, null, null, "CARD_TEXT", null, null,
                null, null, null, null, null, null,
                null, null, null);
        Map<ColorIndicator, Integer> expectedResult = new HashMap<>();
        expectedResult.put(ColorIndicator.G, 3);

        doReturn(expectedResult).when(cardService).getCardManaProduction(cardReadDto.getText());

        var actualResult = deckService.getDeckManaProduction(List.of(cardReadDto));

        assertEquals(expectedResult, actualResult);
    }
}