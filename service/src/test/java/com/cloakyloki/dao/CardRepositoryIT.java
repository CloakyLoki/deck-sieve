package com.cloakyloki.dao;

import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.entity.Manacost;
import com.cloakyloki.entity.enumerated.CardSubType;
import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.util.TestDataProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CardRepositoryIT extends IntegrationTestBase {

    private final CardRepository cardRepository = context.getBean(CardRepository.class);
    private final ManacostRepository manacostRepository = context.getBean(ManacostRepository.class);

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

    @Test
    void shouldReturnCardByName() {
        var expectedCard = TestDataProvider.createMirrorCard();
        cardRepository.save(expectedCard);
        cardRepository.getEntityManager().clear();
        var actualCardList = cardRepository.getCardByName(expectedCard.getName());

        assertThat(actualCardList.size()).isEqualTo(1);
        assertThat(actualCardList.get(0)).isEqualTo(expectedCard);
    }

    @Test
    void shouldReturnAllMatchingCards() {
        var mirrorCard = TestDataProvider.createMirrorCard();
        var mishraCard = TestDataProvider.createMishraCard();
        cardRepository.save(mirrorCard);
        cardRepository.save(mishraCard);
        cardRepository.getEntityManager().clear();

        var filter = CardFilter.builder()
                .cardName("Mirage Mirror")
                .manaValue(999)
                .cardType(null)
                .cardSubType(null)
                .cardSuperType(null)
                .rarity(Rarity.COMMON)
                .text("dummy")
                .flavorText("dummy")
                .power(999)
                .toughness(999)
                .keywords("mishra")
                .build();

        var actualCardList = cardRepository.getCardByAnyParameter(session, filter);

        Assertions.assertAll(
                () -> assertThat(actualCardList.size()).isEqualTo(2),
                () -> assertThat(actualCardList).contains(mirrorCard),
                () -> assertThat(actualCardList).contains(mishraCard)
        );
    }

    @Test
    void shouldReturnAllCardsWithMatchingManacost() {
        var mishraCard = TestDataProvider.createMishraCard();
        var mishraManacost = Manacost.builder()
                .card(mishraCard)
                .blue(1)
                .red(1)
                .black(1)
                .common(1)
                .build();
        cardRepository.save(mishraCard);
        manacostRepository.save(mishraManacost);
        cardRepository.getEntityManager().clear();

        var filter = CardFilter.builder()
                .manacost(mishraManacost)
                .build();

        var actualCardList = cardRepository.getCardByAnyParameter(session, filter);

        assertThat(actualCardList.size()).isEqualTo(1);
        assertThat(actualCardList.get(0)).isEqualTo(mishraCard);
    }

    @Test
    void shouldReturnAllCardsMatchingManavalueAndType() {
        var mirrorCard = TestDataProvider.createMirrorCard();
        cardRepository.save(mirrorCard);
        cardRepository.getEntityManager().clear();

        var filter = CardFilter.builder()
                .manaValue(3)
                .cardType(CardType.ARTIFACT)
                .build();

        var actualCardList = cardRepository.getCardByManaValueAndType(session, filter);

        assertThat(actualCardList.size()).isEqualTo(1);
        assertThat(actualCardList.get(0)).isEqualTo(mirrorCard);
    }

    @Test
    void shouldReturnAllCardsMatchingKeywordOrText() {
        var mirrorCard = TestDataProvider.createMirrorCard();
        cardRepository.save(mirrorCard);
        cardRepository.getEntityManager().clear();

        var filter = CardFilter.builder()
                .keywords("mirror")
                .text("dummy text")
                .build();

        var actualCardList = cardRepository.getCardByTextOrKeyword(session, filter);

        assertThat(actualCardList.size()).isEqualTo(1);
        assertThat(actualCardList.get(0)).isEqualTo(mirrorCard);
    }

    @Test
    void shouldReturnAllCardsBySubtypeAndManavalue() {
        var mirrorCard = TestDataProvider.createMirrorCard();
        cardRepository.save(mirrorCard);
        cardRepository.getEntityManager().clear();
        var filter = CardFilter.builder()
                .cardSubType(CardSubType.AURA)
                .manaValue(3)
                .build();

        var actualCardList = cardRepository.getCardBySubtypeAndManavalue(session, filter);

        assertThat(actualCardList.size()).isEqualTo(1);
        assertThat(actualCardList.get(0)).isEqualTo(mirrorCard);
    }

    @Test
    void shouldReturnAllIfFilterIsEmpty() {
        var mirrorCard = TestDataProvider.createMirrorCard();
        var mishraCard = TestDataProvider.createMishraCard();
        cardRepository.save(mirrorCard);
        cardRepository.save(mishraCard);
        cardRepository.getEntityManager().clear();
        var filter = CardFilter.builder()
                .build();

        var actualCardList = cardRepository.getCardByManaValueAndType(session, filter);

        Assertions.assertAll(
                () -> assertThat(actualCardList.size()).isEqualTo(2),
                () -> assertThat(actualCardList).contains(mirrorCard),
                () -> assertThat(actualCardList).contains(mishraCard)
        );
    }
}