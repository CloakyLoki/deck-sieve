package com.cloakyloki.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;

@Value
@FieldNameConstants
public class DeckCreateUpdateDto {

    @NotBlank
    String deckname;
    Long userIdCreatedBy;
    Boolean favourite;
}
