package com.cloakyloki.dto;

import lombok.Value;

@Value
public class DeckReadDto {

    Long id;
    String name;
    Boolean favourite;
    Long userId;
}
