package com.cloakyloki.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class CSetReadDto {

    String code;
    Integer baseSetSize;
    Integer totalSetSize;
    String name;
    String languages;
    LocalDate releaseDate;
    String type;
    Boolean isFoilOnly;
    Boolean isOnlineOnly;
    String block;
}
