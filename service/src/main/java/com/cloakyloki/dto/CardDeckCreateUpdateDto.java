package com.cloakyloki.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class CardDeckCreateUpdateDto {

    String name;
    Long cardId;
    Long deckId;
}
