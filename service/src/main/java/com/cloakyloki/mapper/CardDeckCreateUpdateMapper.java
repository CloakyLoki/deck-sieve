package com.cloakyloki.mapper;

import com.cloakyloki.dto.CardDeckCreateUpdateDto;
import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.CardDeck;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.repository.CardRepository;
import com.cloakyloki.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardDeckCreateUpdateMapper implements Mapper<CardDeckCreateUpdateDto, CardDeck> {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    @Override
    public CardDeck map(CardDeckCreateUpdateDto fromObject, CardDeck toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public CardDeck map(CardDeckCreateUpdateDto object) {
        CardDeck cardDeck = new CardDeck();
        copy(object, cardDeck);
        return cardDeck;
    }

    //TODO разобраться с Optional. Сейчас некорректно
    private void copy(CardDeckCreateUpdateDto object, CardDeck cardDeck) {
        cardDeck.setCard(cardRepository.findById(object.getCardId())
                .orElse(new Card()));
        cardDeck.setDeck(deckRepository.findById(object.getDeckId())
                .orElse(new Deck()));
    }
}
