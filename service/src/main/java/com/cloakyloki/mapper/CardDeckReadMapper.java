package com.cloakyloki.mapper;

import com.cloakyloki.dto.CardDeckReadDto;
import com.cloakyloki.entity.CardDeck;
import org.springframework.stereotype.Component;

@Component
public class CardDeckReadMapper implements Mapper<CardDeck, CardDeckReadDto> {

    @Override
    public CardDeckReadDto map(CardDeck object) {
        return new CardDeckReadDto(
                object.getCard().getId(),
                object.getDeck().getId()
        );
    }
}
