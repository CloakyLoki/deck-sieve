package com.cloakyloki.service;

import com.cloakyloki.dto.CardDeckCreateUpdateDto;
import com.cloakyloki.dto.CardDeckReadDto;
import com.cloakyloki.mapper.CardDeckCreateUpdateMapper;
import com.cloakyloki.mapper.CardDeckReadMapper;
import com.cloakyloki.repository.CardDeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardDeckService {

    private final CardDeckRepository cardDeckRepository;
    private final CardDeckCreateUpdateMapper cardDeckCreateUpdateMapper;
    private final CardDeckReadMapper cardDeckReadMapper;

    @Transactional
    public CardDeckReadDto create(CardDeckCreateUpdateDto cardDeck) {
        return Optional.of(cardDeck)
                .map(cardDeckCreateUpdateMapper::map)
                .map(cardDeckRepository::saveAndFlush)
                .map(cardDeckReadMapper::map)
                .orElseThrow();
    }
}
