package com.cloakyloki.service;

import com.cloakyloki.dto.CardCreateUpdateDto;
import com.cloakyloki.dto.CardReadDto;
import com.cloakyloki.mapper.CardCreateUpdateMapper;
import com.cloakyloki.mapper.CardReadMapper;
import com.cloakyloki.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardReadMapper cardReadMapper;
    private final CardCreateUpdateMapper cardCreateUpdateMapper;

    public List<CardReadDto> findAll() {
        return cardRepository.findAll().stream()
                .map(cardReadMapper::map)
                .toList();
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
