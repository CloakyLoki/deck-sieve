package com.cloakyloki.service;

import com.cloakyloki.dto.CardCreateUpdateDto;
import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.dto.CardReadDto;
import com.cloakyloki.mapper.CardCreateUpdateMapper;
import com.cloakyloki.mapper.CardReadMapper;
import com.cloakyloki.repository.CardRepository;
import com.cloakyloki.repository.predicate.QPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.cloakyloki.entity.QCard.card;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardReadMapper cardReadMapper;
    private final CardCreateUpdateMapper cardCreateUpdateMapper;

    public Page<CardReadDto> findAll(CardFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.getName(), card.name::containsIgnoreCase)
                .add(filter.getManaValue(), card.manaValue::eq)
                .add(filter.getManacost(), card.manacost::containsIgnoreCase)
                .add(filter.getRarity(), card.rarity::containsIgnoreCase)
                .add(filter.getType(), card.type::containsIgnoreCase)
                .add(filter.getSubtype(), card.subtype::containsIgnoreCase)
                .add(filter.getSupertype(), card.supertype::containsIgnoreCase)
                .add(filter.getText(), card.text::containsIgnoreCase)
                .add(filter.getFlavorText(), card.flavorText::containsIgnoreCase)
                .add(filter.getKeywords(), card.keywords::containsIgnoreCase)
                .add(filter.getPower(), card.power::eq)
                .add(filter.getArtist(), card.artist::containsIgnoreCase)
                .add(filter.getToughness(), card.toughness::eq)
                .add(filter.getFrameVersion(), card.frameVersion::eq)
                .add(filter.getIsBanned(), card.isBanned::eq)
                .buildAnd();
        return cardRepository.findAll(predicate, pageable)
                .map(cardReadMapper::map);
    }


    public Optional<CardReadDto> findById(Long id) {
        return cardRepository.findById(id)
                .map(cardReadMapper::map);
    }

    @Transactional
    public CardReadDto create(CardCreateUpdateDto card) {
        return Optional.of(card)
                .map(cardCreateUpdateMapper::map)
                .map(cardRepository::saveAndFlush)
                .map(cardReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CardReadDto> update(Long id, CardCreateUpdateDto cardCreateUpdateDto) {
        return cardRepository.findById(id)
                .map(userEntity -> cardCreateUpdateMapper.map(cardCreateUpdateDto, userEntity))
                .map(cardRepository::saveAndFlush)
                .map(cardReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return cardRepository.findById(id)
                .map(card -> {
                    cardRepository.delete(card);
                    cardRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
