package com.cloakyloki.entity;

import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CardIT extends IntegrationTestBase {

    private Session session;

    @BeforeEach
    void sessionInit() {
        this.session = sessionFactory.openSession();
        this.session.beginTransaction();
    }

    @AfterEach
    void cleanAfterTest() {
        this.session.getTransaction().rollback();
        this.session.close();
    }

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
        session.save(expectedCard);
        session.clear();

        var actualCard = session.get(Card.class, expectedCard.getId());

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
        session.save(expectedCard);
        session.clear();

        var actualCard = session.get(Card.class, expectedCard.getId());

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
        session.save(expectedCard);
        session.clear();
        expectedCard.setIsBanned(true);
        session.update(expectedCard);
        session.flush();
        session.clear();

        var actualCard = session.get(Card.class, expectedCard.getId());

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
        session.save(card);
        session.clear();

        assertThat(session.get(Card.class, card.getId()).getId()).isNotNull();

        session.clear();
        session.delete(card);
        session.flush();
        session.clear();

        assertNull(session.get(Card.class, card.getId()));
    }
}