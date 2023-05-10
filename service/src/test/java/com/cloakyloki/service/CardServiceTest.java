package com.cloakyloki.service;

import com.cloakyloki.mapper.CardCreateUpdateMapper;
import com.cloakyloki.mapper.CardReadMapper;
import com.cloakyloki.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;
    @Mock
    private CardReadMapper cardReadMapper;
    @Mock
    private CardCreateUpdateMapper cardCreateUpdateMapper;
    @InjectMocks
    private CardService cardService;

//    @Test
//    void findAll() {
//        var cardReadDto = TestDataProvider.createCardReadDto();
//        var mirrorCard = TestDataProvider.createMirrorCard();
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
//        QPredicate.builder().add(filter.name(), mirrorCard.getName()::equals).buildAnd();
//        Mockito.doReturn();
//    }

    @Test
    void findAllByDeckId() {
    }


    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAverageManaValue() {
    }

    @Test
    void getNumberOfEachColor() {
    }

    @Test
    void getManaCurve() {
    }

    @Test
    void getCardManaProduction() {
    }
}