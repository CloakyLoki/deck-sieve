package com.cloakyloki.dto;

import com.cloakyloki.entity.Manacost;
import com.cloakyloki.entity.enumerated.CardSubType;
import com.cloakyloki.entity.enumerated.CardSuperType;
import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CardFilter {

    String cardName;
    Integer manaValue;
    Manacost manacost;
    CardType cardType;
    CardSubType cardSubType;
    CardSuperType cardSuperType;
    Rarity rarity;
    String text;
    String flavorText;
    String keywords;
    Integer power;
    Integer toughness;
    Boolean isBanned;
}