package com.cloakyloki.dto;

import lombok.Value;

import javax.validation.constraints.PositiveOrZero;

@Value
public class CardReadDto {

    Long id;
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
    String setcode;
}
