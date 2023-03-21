package com.cloakyloki.entity;

import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import com.cloakyloki.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CardIT extends IntegrationTestBase {

    @Test
    void addCard() {
        var expectedCard = Card.builder()
                .name("Mirage Mirror")
                .flavorText("Most mirrors show your reflection. Other show your potential")
                .isBanned(false)
                .keywords("mirror")
                .manaValue(3)
                .type(CardType.ARTIFACT)
                .purchaseUrl("https://www.tcgplayer.com/product/134930")
                .rarity(Rarity.RARE)
                .scryfallIllustrationId("1562793459")
                .text("Mirage Mirror becomes a copy of target artifact, creature, enchantment, or land until end of turn.")
                .build();
        entityManager.persist(expectedCard);
        entityManager.clear();

        var actualCard = entityManager.find(Card.class, expectedCard.getId());

        assertThat(actualCard.getId()).isNotNull();
    }

    @Test
    void getCard() {
        var expectedCard = Card.builder()
                .name("Mirage Mirror")
                .flavorText("Most mirrors show your reflection. Other show your potential")
                .isBanned(false)
                .keywords("mirror")
                .manaValue(3)
                .type(CardType.ARTIFACT)
                .purchaseUrl("https://www.tcgplayer.com/product/134930")
                .rarity(Rarity.RARE)
                .scryfallIllustrationId("1562793459")
                .text("Mirage Mirror becomes a copy of target artifact, creature, enchantment, or land until end of turn.")
                .build();
        entityManager.persist(expectedCard);
        entityManager.clear();

        var actualCard = entityManager.find(Card.class, expectedCard.getId());

        assertEquals(expectedCard, actualCard);
    }

    @Test
    void updateCard() {
        var expectedCard = Card.builder()
                .name("Mishra Eminent One")
                .type(CardType.CREATURE)
                .rarity(Rarity.SPECIAL)
                .text("Nice commander ;)")
                .keywords("commander")
                .isBanned(false)
                .build();
        entityManager.persist(expectedCard);
        entityManager.clear();
        expectedCard.setIsBanned(true);
        entityManager.merge(expectedCard);
        entityManager.flush();
        entityManager.clear();

        var actualCard = entityManager.find(Card.class, expectedCard.getId());

        assertEquals(expectedCard, actualCard);
    }

    @Test
    void deleteCard() {
        var card = Card.builder()
                .name("Mishra Eminent One")
                .type(CardType.CREATURE)
                .rarity(Rarity.SPECIAL)
                .text("Nice commander ;)")
                .keywords("commander")
                .isBanned(false)
                .build();
        entityManager.persist(card);
        entityManager.clear();

        assertThat(entityManager.find(Card.class, card.getId()).getId()).isNotNull();

        entityManager.clear();
        entityManager.remove(card);
        entityManager.flush();
        entityManager.clear();

        assertNull(entityManager.find(Card.class, card.getId()));
    }
}