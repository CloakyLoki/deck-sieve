package com.cloakyloki.dao;

import com.cloakyloki.integration.annotation.IT;
import com.cloakyloki.util.TestDataProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
class CardRepositoryIT {

    private final CardRepository cardRepository;

    @Test
    void deleteCard() {
        var card = TestDataProvider.createMirrorCard();
        cardRepository.save(card);

        cardRepository.delete(card);

        assertThat(cardRepository.findById(card.getId())).isEmpty();
    }

    @Test
    void updateCard() {
        var card = TestDataProvider.createMirrorCard();
        cardRepository.save(card);
        cardRepository.getEntityManager().clear();

        card.setIsBanned(true);
        cardRepository.update(card);
        cardRepository.getEntityManager().clear();

        assertThat(cardRepository.findById(card.getId())).isEqualTo(Optional.of(card));
    }

    @Test
    void createCard() {
        var card = TestDataProvider.createMirrorCard();
        cardRepository.save(card);
        cardRepository.getEntityManager().clear();

        assertThat(cardRepository.findById(card.getId())).isNotEmpty();
    }

    @Test
    void findCardById() {
        var card = TestDataProvider.createMirrorCard();
        cardRepository.save(card);
        cardRepository.getEntityManager().clear();

        assertThat(cardRepository.findById(card.getId())).isEqualTo(Optional.of(card));
    }
}