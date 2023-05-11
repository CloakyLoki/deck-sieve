package com.cloakyloki.service;

import com.cloakyloki.dto.CardReadDto;
import com.cloakyloki.dto.ManacostDto;
import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.enumerated.ColorIndicator;
import com.cloakyloki.mapper.CardReadMapper;
import com.cloakyloki.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    private static final Long DECK_ID = 1L;
    private static final Long CARD_ID = 1L;
    private static final String CARD_NAME = "MTGCard";
    private static final Float AVERAGE = 4.0F;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.000");
    private static final String CARD_TEXT = "Other Elves you control get +1/+1.\n" +
            "{T}: Add {G}{G}{G}.";

    @Mock
    private CardRepository cardRepository;
    @Mock
    private CardReadMapper cardReadMapper;
    @InjectMocks
    private CardService cardService;

//    @Test
//    void findAll() {
//        var cardReadDto = TestDataProvider.createCardReadDto();
//        CardFilter filter = new CardFilter(cardReadDto.getName(),
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null);
//        QPredicate.builder()
//                .add(filter.name(), it-> it)
//                .buildAnd();
//        Mockito.doReturn(List.of(cardReadDto)).when(cardRepository).findAll();
//    }

    @Test
    void findAllByDeckId() {
        var card = new Card(CARD_ID, CARD_NAME, null,
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
        var cardReadDto = new CardReadDto(CARD_ID, CARD_NAME, null, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null);

        doReturn(List.of(card)).when(cardRepository).findAllByDeckId(DECK_ID);
        doReturn(cardReadDto).when(cardReadMapper).map(card);

        var expectedResult = List.of(cardReadDto);
        var actualResult = cardService.findAllByDeckId(DECK_ID);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnAverageValue() {
        var card1 = new CardReadDto(CARD_ID, CARD_NAME, 2, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null);
        var card2 = new CardReadDto(CARD_ID, CARD_NAME, 6, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null);
        var actualResult = cardService.getAverageManaValue(List.of(card1, card2));
        var expectedResult = DECIMAL_FORMAT.format(AVERAGE);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnZeroByEmptyManavalue() {
        var card = new CardReadDto(CARD_ID, CARD_NAME, 0, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null);
        var actualResult = cardService.getAverageManaValue(List.of(card));
        var expectedResult = DECIMAL_FORMAT.format(0);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getNumberOfEachColor() {
        ManacostDto manacostDto = new ManacostDto("1", List.of(ColorIndicator.B, ColorIndicator.G));
        var card = new CardReadDto(CARD_ID, CARD_NAME, null, manacostDto, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null);
        Map<ColorIndicator, Integer> expectedResult = new HashMap<>();
        expectedResult.put(ColorIndicator.B, 1);
        expectedResult.put(ColorIndicator.G, 1);

        var actualResult = cardService.getNumberOfEachColor(List.of(card));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getManaCurve() {
        var cards = List.of(new CardReadDto(CARD_ID, CARD_NAME, 2, null, null,
                null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null));
        Integer cardsInDeck = cards.size();
        Integer manavalue = cards.get(0).getManaValue();
        Map<Integer, Integer> expectedResult = new HashMap<>();

        expectedResult.put(manavalue, cardsInDeck);
        var actualResult = cardService.getManaCurve(cards);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getCardManaProduction() {
        Map<ColorIndicator, Integer> expectedResult = new HashMap<>();
        expectedResult.put(ColorIndicator.G, 3);
        var actualResult = cardService.getCardManaProduction(CARD_TEXT);

        assertEquals(expectedResult, actualResult);
    }
}