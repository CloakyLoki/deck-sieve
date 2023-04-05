package com.cloakyloki.repository.builder;

import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CardBuilder {

    public static Card createBlankCard(String cardname, Boolean isBanned) {
        return Card.builder()
                .name(cardname)
                .isBanned(isBanned)
                .build();
    }

    public static Card createBasicCard(String cardname,
                                       Integer manavalue,
                                       CardType cardType,
                                       Rarity rarity,
                                       Boolean isBanned
    ) {
        return Card.builder()
                .name(cardname)
                .manaValue(manavalue)
                .type(cardType)
                .rarity(rarity)
                .isBanned(isBanned)
                .build();
    }

}
