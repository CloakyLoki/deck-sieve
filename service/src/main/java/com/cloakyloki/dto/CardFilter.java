package com.cloakyloki.dto;

public record CardFilter(
        String name,
        Integer manaValue,
        String manacost,
        String rarity,
        String type,
        String subtype,
        String supertype,
        String text,
        String flavorText,
        String keywords,
        String power,
        String artist,
        String toughness,
        String purchaseUrl,
        String mvid,
        String scryfallIllustrationId,
        String frameVersion,
        Boolean isBanned,
        String setcode) {
}