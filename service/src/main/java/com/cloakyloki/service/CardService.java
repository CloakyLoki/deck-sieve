package com.cloakyloki.service;

import com.cloakyloki.dto.CardCreateUpdateDto;
import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.dto.CardReadDto;
import com.cloakyloki.entity.enumerated.ColorIndicator;
import com.cloakyloki.mapper.CardCreateUpdateMapper;
import com.cloakyloki.mapper.CardReadMapper;
import com.cloakyloki.mapper.ColorMapper;
import com.cloakyloki.repository.CardRepository;
import com.cloakyloki.repository.predicate.QPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cloakyloki.entity.QCard.card;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardReadMapper cardReadMapper;
    private final CardCreateUpdateMapper cardCreateUpdateMapper;
    private final ColorMapper colorMapper;

    public Page<CardReadDto> findAll(CardFilter filter, Pageable pageable) {
        var predicate = QPredicate.builder()
                .add(filter.name(), card.name::containsIgnoreCase)
                .add(filter.manaValue(), card.manaValue::eq)
                .add(filter.manacost(), card.manacost::containsIgnoreCase)
                .add(filter.rarity(), card.rarity::containsIgnoreCase)
                .add(filter.type(), card.type::containsIgnoreCase)
                .add(filter.subtype(), card.subtype::containsIgnoreCase)
                .add(filter.supertype(), card.supertype::containsIgnoreCase)
                .add(filter.text(), card.text::containsIgnoreCase)
                .add(filter.flavorText(), card.flavorText::containsIgnoreCase)
                .add(filter.keywords(), card.keywords::containsIgnoreCase)
                .add(filter.power(), card.power::eq)
                .add(filter.artist(), card.artist::containsIgnoreCase)
                .add(filter.toughness(), card.toughness::eq)
                .add(filter.frameVersion(), card.frameVersion::eq)
                .add(filter.isBanned(), card.isBanned::eq)
                .add(filter.setcode(), card.setcode.code::eq)
                .buildAnd();
        return cardRepository.findAll(predicate, pageable)
                .map(cardReadMapper::map);
    }

    public List<CardReadDto> findAllByDeckId(Long deckId) {
        return cardRepository.findAllByDeckId(deckId).stream()
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

    public String getAverageManaValue(List<CardReadDto> cards) {
        DecimalFormat df = new DecimalFormat("0.000");
        double averageManavalue = cards.stream()
                .mapToDouble(CardReadDto::getManaValue)
                .average()
                .orElse(0);
        return df.format(averageManavalue);
    }

    public Map<ColorIndicator, Integer> getNumberOfEachColor(List<CardReadDto> cards) {
        Map<ColorIndicator, Integer> colorNumbers = new HashMap<>();
        for (CardReadDto card : cards) {
            if (card.getManacost() != null) {
                var colorList = card.getManacost().getColorList();
                for (ColorIndicator color : colorList) {
                    if (colorNumbers.containsKey(color)) {
                        colorNumbers.put(color, colorNumbers.get(color) + 1);
                    } else {
                        colorNumbers.put(color, 1);
                    }
                }
            }

        }
        return colorNumbers;
    }

    public Map<Integer, Integer> getManaCurve(List<CardReadDto> cards) {
        Map<Integer, Integer> manaCurve = new HashMap<>();
        for (CardReadDto card : cards) {
            var manaValue = card.getManaValue();
            if (manaCurve.containsKey(manaValue)) {
                manaCurve.put(manaValue, manaCurve.get(manaValue) + 1);
            } else {
                manaCurve.put(manaValue, 1);
            }
        }
        return manaCurve;
    }

    public Map<ColorIndicator, Integer> getCardManaProduction(String cardtext) {
        Map<ColorIndicator, Integer> manaProduction = new HashMap<>();

        Pattern pattern = Pattern.compile("\\{T\\}: Add ((\\{\\w+\\},?)+)\\.");
        Matcher matcher = pattern.matcher(cardtext);

        if (matcher.find()) {
            String manaSymbols = matcher.group(1);
            Pattern colorPattern = Pattern.compile("\\{(\\w+)\\}");
            Matcher colorMatcher = colorPattern.matcher(manaSymbols);

            while (colorMatcher.find()) {
                String manaSymbol = colorMatcher.group(1);
                ColorIndicator color = ColorIndicator.valueOf(manaSymbol);
                manaProduction.merge(color, 1, Integer::sum);
            }
            return manaProduction;
        }
        return null;
    }
}
