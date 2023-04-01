package com.cloakyloki.dao;

import com.cloakyloki.dao.repository.CardRepository;
import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.integration.annotation.IT;
import com.cloakyloki.util.TestDataProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@RequiredArgsConstructor
class CardRepositoryIT {

    private final CardRepository cardRepository;
    private final EntityManager entityManager;

    @Test
    void delete() {
        var card = TestDataProvider.createMirrorCard();
        entityManager.persist(card);

        cardRepository.delete(card);
        entityManager.flush();
        entityManager.clear();

        assertThat(cardRepository.findById(card.getId())).isEmpty();
    }

    @Test
    void update() {
        var card = TestDataProvider.createMirrorCard();
        entityManager.persist(card);

        card.setIsBanned(true);
        cardRepository.saveAndFlush(card);
        entityManager.clear();

        assertThat(cardRepository.findById(card.getId())).isEqualTo(Optional.of(card));
    }

    @Test
    void create() {
        var card = TestDataProvider.createMirrorCard();
        cardRepository.save(card);
        entityManager.clear();

        assertThat(cardRepository.findById(card.getId())).isNotEmpty();
    }

    @Test
    void findById() {
        var card = TestDataProvider.createMirrorCard();
        entityManager.persist(card);
        entityManager.clear();

        assertThat(cardRepository.findById(card.getId())).isEqualTo(Optional.of(card));
    }

    @Test
    void findAllByFilter() {
        var mirrorCard = TestDataProvider.createMirrorCard();
        var mishraCard = TestDataProvider.createMishraCard();
        var filter = CardFilter.builder()
                .cardName("mi")
                .build();
        entityManager.persist(mirrorCard);
        entityManager.persist(mishraCard);

        var cards = cardRepository.findAllByFilter(filter);

        assertThat(cards).hasSize(2);
    }
}