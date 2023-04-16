package com.cloakyloki.mapper;


import com.cloakyloki.dto.DeckCreateUpdateDto;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.entity.User;
import com.cloakyloki.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class DeckCreateUpdateMapper implements Mapper<DeckCreateUpdateDto, Deck> {

    UserRepository userRepository;

    @Override
    public Deck map(DeckCreateUpdateDto fromObject, Deck toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Deck map(DeckCreateUpdateDto object) {
        Deck deck = new Deck();
        copy(object, deck);
        return deck;
    }

    private void copy(DeckCreateUpdateDto object, Deck deck) {
        deck.setName(object.getName());
        deck.setFavourite(object.getFavourite());
        deck.setUser(userRepository.findById(object.getUserIdCreatedBy()).orElse(new User()));
    }
}