package com.cloakyloki.dto;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CardFilter {

    String cardName;
    Integer manaValue;
    String text;
    String flavorText;
    String keywords;
}