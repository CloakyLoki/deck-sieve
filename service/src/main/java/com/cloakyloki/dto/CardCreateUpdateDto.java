package com.cloakyloki.dto;

import com.cloakyloki.entity.enumerated.Rarity;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class CardCreateUpdateDto {

    String name;
    Integer manaValue;
    String manacost;
    Rarity rarity;
    String type;
    String subtype;
    String supertype;
    String text;
    String flavorText;
    String keywords;
    String power;
    String toughness;
    String artist;
    String purchaseUrl;
    String scryfallIllustrationId;
    Boolean isBanned;
}
