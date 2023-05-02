package com.cloakyloki.service;

import com.cloakyloki.dto.DeckCreateUpdateDto;
import com.cloakyloki.dto.DeckReadDto;
import com.cloakyloki.mapper.DeckCreateUpdateMapper;
import com.cloakyloki.mapper.DeckReadMapper;
import com.cloakyloki.repository.DeckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeckService {

    private final DeckRepository deckRepository;
    private final DeckReadMapper deckReadMapper;
    private final DeckCreateUpdateMapper deckCreateUpdateMapper;

    public List<DeckReadDto> findAllByUserId(Long userId) {
        return deckRepository.findAllByUserId(userId).stream()
                .map(deckReadMapper::map)
                .toList();
    }

    public Optional<DeckReadDto> findById(Long id) {
        return deckRepository.findById(id)
                .map(deckReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return deckRepository.findById(id)
                .map(deckEntity -> {
                    deckRepository.delete(deckEntity);
                    deckRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public DeckReadDto create(DeckCreateUpdateDto deck) {
        return Optional.of(deck)
                .map(deckCreateUpdateMapper::map)
                .map(deckRepository::saveAndFlush)
                .map(deckReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<DeckReadDto> update(Long id, DeckCreateUpdateDto deckCreateUpdateDto) {
        return deckRepository.findById(id)
                .map(userEntity -> deckCreateUpdateMapper.map(deckCreateUpdateDto, userEntity))
                .map(deckRepository::saveAndFlush)
                .map(deckReadMapper::map);
    }
}
