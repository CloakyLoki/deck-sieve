package com.cloakyloki.integration.repository;

import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.repository.CardRepository;
import com.cloakyloki.repository.predicate.QPredicate;
import com.cloakyloki.util.TestDataProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.cloakyloki.entity.QCard.card;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class CardRepositoryIT extends IntegrationTestBase {

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
        mirrorCard.setName("test");
        var filter = CardFilter.builder()
                .cardName("mi")
                .build();
        var predicate = QPredicate.builder()
                .add(filter.getCardName(), card.name::containsIgnoreCase)
                .add(filter.getManaValue(), card.manaValue::eq)
                .buildAnd();
        entityManager.persist(mirrorCard);
        entityManager.persist(mishraCard);
        entityManager.clear();

        var cards = cardRepository.findAll(predicate);

        assertThat(cards).hasSize(1);
    }
}