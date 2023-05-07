package com.cloakyloki.util;

import com.cloakyloki.dto.CardCreateUpdateDto;
import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.entity.CSet;
import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.CardDeck;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.entity.User;
import com.cloakyloki.entity.enumerated.CardSubType;
import com.cloakyloki.entity.enumerated.CardSuperType;
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
                .type("Creature")
                .subtype("Artificier")
                .purchaseUrl("https://www.tcgplayer.com/product/14296")
                .scryfallIllustrationId("1562940254")
                .text("Whenever you cast an artifact spell, you may search your graveyard, hand, " +
                        "and/or library for a card with the same name as that spell and put it onto the battlefield. " +
                        "If you search your library this way, shuffle.")
                .setcode(createCardset())
                .build();
    }

    public static Card createMirrorCard() {
        return Card.builder()
                .name("Mirage Mirror")
                .flavorText("Most mirrors show your reflection. Other show your potential")
                .isBanned(false)
                .keywords("mirror")
                .manaValue(3)
                .type("Artifact")
                .subtype("Aura")
                .purchaseUrl("https://www.tcgplayer.com/product/134930")
                .scryfallIllustrationId("1562793459")
                .text("Mirage Mirror becomes a copy of target artifact, creature, enchantment, " +
                        "or land until end of turn.")
                .setcode(createCardset())
                .build();
    }

    public static CSet createCardset() {
        return CSet.builder()
                .code("AER")
                .name("Test cardset")
                .build();
    }

    public static Deck createTestDeck(User user) {
        return Deck.builder()
                .user(user)
                .favourite(true)
                .name("testDeck")
                .build();
    }

    public static User createTestUser() {
        return User.builder()
                .username("CloakyLoki")
                .role(Role.USER)
                .password("123")
                .isActive(true)
                .build();
    }

    public static CardDeck createTestCardDeck() {
        return CardDeck.builder()
                .name("testname")
                .deck(TestDataProvider.createTestDeck(TestDataProvider.createTestUser()))
                .card(TestDataProvider.createMishraCard())
                .build();
    }

    public static CardCreateUpdateDto createCardUpdateDto() {
        return new CardCreateUpdateDto(
                "testName",
                3,
                "testManaCost",
                Rarity.COMMON.toString(),
                CardType.DRAGON.toString(),
                CardSubType.ALIEN.toString(),
                CardSuperType.LEGENDARY.toString(),
                "testText",
                "testFlavorText",
                "testKeywords",
                "5",
                "testArtist",
                "7",
                "testUrl",
                "testMvId",
                "a3be6d48-dee7-4c86-910c-b76c155b50b9",
                "2020",
                Boolean.FALSE,
                "ABC"
        );
    }

    public static CardFilter createCardFilter() {
        return new CardFilter("Mishra, Artificer Prodigy",
                1,
                "testManacost",
                "testRarity",
                "testType",
                "testSubtype",
                "testSupertype",
                "testText",
                "testFlafovorText",
                "testKeyword",
                "testPower",
                "testArtist",
                "testToughness",
                "testUrl",
                "testMvid",
                "testIllId",
                "testFrame",
                false,
                "testSetcode");
    }
}