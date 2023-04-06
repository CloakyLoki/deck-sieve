package com.cloakyloki.repository.builder;

import com.cloakyloki.entity.Card;
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
                                       String cardType,
                                       Boolean isBanned
    ) {
        return Card.builder()
                .name(cardname)
                .manaValue(manavalue)
                .type(cardType)
                .isBanned(isBanned)
                .build();
    }

}
