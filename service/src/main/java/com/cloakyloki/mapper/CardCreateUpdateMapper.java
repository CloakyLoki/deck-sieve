package com.cloakyloki.mapper;

import com.cloakyloki.dto.CardCreateUpdateDto;
import com.cloakyloki.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardCreateUpdateMapper implements Mapper<CardCreateUpdateDto, Card> {

    @Override
    public Card map(CardCreateUpdateDto fromObject, Card toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Card map(CardCreateUpdateDto object) {
        Card card = new Card();
        copy(object, card);
        return card;
    }

    private void copy(CardCreateUpdateDto object, Card card) {
        card.setName(object.getName());
        card.setManaValue(object.getManaValue());
        card.setManacost(object.getManacost());
        card.setRarity(object.getRarity());
        card.setType(object.getType());
        card.setSubtype(object.getSubtype());
        card.setSupertype(object.getSupertype());
        card.setText(object.getText());
        card.setFlavorText(object.getFlavorText());
        card.setKeywords(object.getKeywords());
        card.setPower(object.getPower());
        card.setArtist(object.getArtist());
        card.setToughness(object.getToughness());
        card.setPurchaseUrl(object.getPurchaseUrl());
        card.setMvid(object.getMvid());
        card.setScryfallIllustrationId(object.getScryfallIllustrationId());
        card.setFrameVersion(object.getFrameVersion());
        card.setIsBanned(object.getIsBanned());
    }
}
