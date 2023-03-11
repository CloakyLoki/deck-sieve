package com.cloakyloki.dao;

import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.Manacost;
import com.cloakyloki.entity.enumerated.CardSubType;
import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.util.CardProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

class CardFinderIT extends IntegrationTestBase {

    //    private final CardDao cardDao = CardDao.getInstance();
    EntityManager entityManager;
    CardRepository cardRepository = new CardRepository(Card.class, session);


    @Test
    void delete() {
        var expectedCard = CardProvider.createMirrorCard();
        session.save(expectedCard);
        session.clear();
        assertThat(session.get(Card.class, expectedCard.getId())).isNotNull();
        cardRepository.delete(expectedCard.getId());
//        assertThat(session.get(Card.class, expectedCard.getId())).isNull();


    }

    @Test
    void shouldReturnCardByName() {
        var expectedCard = CardProvider.createMirrorCard();
        session.save(expectedCard);
        session.clear();
        var actualCardList = cardRepository.getCardByName(session, expectedCard.getName());

        assertThat(actualCardList.size()).isEqualTo(1);
        assertThat(actualCardList.get(0)).isEqualTo(expectedCard);
    }

    @Test
    void shouldReturnAllMatchingCards() {
        var mirrorCard = CardProvider.createMirrorCard();
        var mishraCard = CardProvider.createMishraCard();
        session.save(mirrorCard);
        session.save(mishraCard);
        session.clear();

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
        var mishraCard = CardProvider.createMishraCard();
        var mishraManacost = Manacost.builder()
                .card(mishraCard)
                .blue(1)
                .red(1)
                .black(1)
                .common(1)
                .build();
        session.save(mishraCard);
        session.save(mishraManacost);
        session.clear();

        var filter = CardFilter.builder()
                .manacost(mishraManacost)
                .build();

        var actualCardList = cardRepository.getCardByAnyParameter(session, filter);

        assertThat(actualCardList.size()).isEqualTo(1);
        assertThat(actualCardList.get(0)).isEqualTo(mishraCard);
    }

    @Test
    void shouldReturnAllCardsMatchingManavalueAndType() {
        var mirrorCard = CardProvider.createMirrorCard();
        session.save(mirrorCard);
        session.clear();

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
        var mirrorCard = CardProvider.createMirrorCard();
        session.save(mirrorCard);
        session.clear();

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
        var mirrorCard = CardProvider.createMirrorCard();
        session.save(mirrorCard);
        session.clear();
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
        var mirrorCard = CardProvider.createMirrorCard();
        var mishraCard = CardProvider.createMishraCard();
        session.save(mirrorCard);
        session.save(mishraCard);
        session.clear();
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
