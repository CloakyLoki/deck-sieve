package com.cloakyloki.mapper;

import com.cloakyloki.dto.DeckReadDto;
import com.cloakyloki.entity.Deck;
import org.springframework.stereotype.Component;

@Component
public class DeckReadMapper implements Mapper<Deck, DeckReadDto> {

    @Override
    public DeckReadDto map(Deck deck) {
        return new DeckReadDto(
                deck.getId(),
                deck.getName(),
                deck.getFavourite(),
                deck.getUser().getId());
    }
}