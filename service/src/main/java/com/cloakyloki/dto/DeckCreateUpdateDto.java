package com.cloakyloki.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class DeckCreateUpdateDto {

    String deckname;
    Long userIdCreatedBy;
    Boolean favourite;
}
