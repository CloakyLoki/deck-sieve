package com.cloakyloki.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.PositiveOrZero;

@Value
@FieldNameConstants
public class CardCreateUpdateDto {

    String name;

    @PositiveOrZero
    Integer manaValue;
    String manacost;
    String rarity;
    String type;
    String subtype;
    String supertype;
    String text;
    String flavorText;
    String keywords;
    String power;
    String artist;
    String toughness;
    String purchaseUrl;
    String mvid;
    String scryfallIllustrationId;
    String frameVersion;
    Boolean isBanned;
}
