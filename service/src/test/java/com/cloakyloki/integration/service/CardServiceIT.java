package com.cloakyloki.integration.service;

import com.cloakyloki.dto.CardReadDto;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.repository.CardRepository;
import com.cloakyloki.service.CardService;
import com.cloakyloki.util.TestDataProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RequiredArgsConstructor
class CardServiceIT extends IntegrationTestBase {

    private final CardService cardService;
    private final CardRepository cardRepository;
    private final EntityManager entityManager;

//    @Test
//    void findAllPageSize() {
//        var testCard = TestDataProvider.createMishraCard();
//        CardFilter filter = TestDataProvider.createCardFilter();
//        PageRequest pageRequest = PageRequest.of(0, 20);
//        cardRepository.save(testCard);
//        entityManager.clear();
//
//        Assertions.assertThat(cardService.findAll(filter, pageRequest).getContent().size()).isEqualTo(1);
//    }
//
//    @Test
//    void findAllContent() {
//        var testCard = TestDataProvider.createMishraCard();
//        CardFilter filter = TestDataProvider.createCardFilter();
//        PageRequest pageRequest = PageRequest.of(0, 20);
//        cardRepository.save(testCard);
//        entityManager.clear();
//
//        Assertions.assertThat(cardService.findAll(filter, pageRequest)
//                .getContent()
//                .get(0)
//                .getId())
//                .isEqualTo(testCard.getId());
//    }

    @Test
    void findById() {
        var testCard = TestDataProvider.createMishraCard();
        cardRepository.save(testCard);
        entityManager.clear();

        Optional<CardReadDto> maybeCard = cardService.findById(testCard.getId());

        assertTrue(maybeCard.isPresent());
        maybeCard.ifPresent(cardReadDto -> assertEquals("Mishra, Artificer Prodigy", cardReadDto.getName()));
    }
}