package com.cloakyloki.util;

import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.CardDeck;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.entity.Manacost;
import com.cloakyloki.entity.User;
import com.cloakyloki.entity.enumerated.CardSubType;
import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import com.cloakyloki.entity.enumerated.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestDataProvider {

    public static Card createMishraCard() {
        return Card.builder()
                .name("Mishra, Artificer Prodigy")
                .flavorText("A sojourn through time gave dark inspiration to one gifted young mind.")
                .isBanned(false)
                .keywords("mishra")
                .manaValue(4)
                .type(CardType.CREATURE)
                .subtype(CardSubType.ARTIFICER)
                .purchaseUrl("https://www.tcgplayer.com/product/14296")
                .rarity(Rarity.RARE)
                .scryfallIllustrationId("1562940254")
                .text("Whenever you cast an artifact spell, you may search your graveyard, hand, " +
                        "and/or library for a card with the same name as that spell and put it onto the battlefield. " +
                        "If you search your library this way, shuffle.")
                .build();
    }

    public static Card createMirrorCard() {
        return Card.builder()
                .name("Mirage Mirror")
                .flavorText("Most mirrors show your reflection. Other show your potential")
                .isBanned(false)
                .keywords("mirror")
                .manaValue(3)
                .type(CardType.ARTIFACT)
                .subtype(CardSubType.AURA)
                .purchaseUrl("https://www.tcgplayer.com/product/134930")
                .rarity(Rarity.RARE)
                .scryfallIllustrationId("1562793459")
                .text("Mirage Mirror becomes a copy of target artifact, creature, enchantment, " +
                        "or land until end of turn.")
                .build();
    }

    public static Deck createTestDeck(User user) {
        return Deck.builder()
                .user(user)
                .favourite(true)
                .build();
    }

    public static User createTestUser() {
        return User.builder()
                .nickname("CloakyLoki")
                .role(Role.USER)
                .password("123")
                .build();
    }

    public static CardDeck createTestCardDeck() {
        return CardDeck.builder()
                .name("testname")
                .deck(TestDataProvider.createTestDeck(TestDataProvider.createTestUser()))
                .card(TestDataProvider.createMishraCard())
                .build();
    }

    public static Manacost createTestManacost(Card card) {
        return Manacost.builder()
                .card(card)
                .blue(1)
                .red(2)
                .black(3)
                .common(2)
                .build();
    }
}