package com.cloakyloki.mapper;

import com.cloakyloki.dto.CardReadDto;
import com.cloakyloki.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardReadMapper implements Mapper<Card, CardReadDto> {

    private static final String IMAGE_FORMAT = "%s/%s/%s.jpg";

    @Override
    public CardReadDto map(Card card) {
        return new CardReadDto(
                card.getId(),
                card.getName(),
                card.getManaValue(),
                card.getManacost(),
                card.getRarity(),
                card.getType(),
                card.getSubtype(),
                card.getSupertype(),
                card.getText(),
                card.getFlavorText(),
                card.getKeywords(),
                card.getPower(),
                card.getArtist(),
                card.getToughness(),
                card.getPurchaseUrl(),
                card.getMvid(),
                getScryfallImagePath(card.getScryfallIllustrationId()),
                card.getFrameVersion(),
                card.getIsBanned(),
                card.getSetcode().getCode()
        );
    }

    private String getScryfallImagePath(String imageId) {
        return String.format(IMAGE_FORMAT, imageId.charAt(0), imageId.charAt(1), imageId);
    }
}
