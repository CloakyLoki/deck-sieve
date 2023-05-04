package com.cloakyloki.http.controller;

import com.cloakyloki.dto.CardDeckCreateUpdateDto;
import com.cloakyloki.dto.CardReadDto;
import com.cloakyloki.dto.DeckReadDto;
import com.cloakyloki.service.CardDeckService;
import com.cloakyloki.service.CardService;
import com.cloakyloki.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/decks")
public class DeckController {

    private final DeckService deckService;
    private final CardService cardService;
    private final CardDeckService cardDeckService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        DeckReadDto deckReadDto = deckService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<CardReadDto> cardsInDeck = cardService.findAllByDeckId(id);
        var numberOfEachColor = cardService.getNumberOfEachColor(cardsInDeck);
        String averageManaValue = cardService.getAverageManaValue(cardsInDeck);
        var manaCurve = cardService.getManaCurve(cardsInDeck);
        model.addAttribute("deck", deckReadDto);
        model.addAttribute("cards", cardsInDeck);
        model.addAttribute("averageManaValue", averageManaValue);
        model.addAttribute("numberOfEachColor", numberOfEachColor);
        model.addAttribute("manacurve", manaCurve);
        return "deckview/deck";
    }

    @PostMapping("/{deckId}/{cardId}/add")
    public String addCardToDeck(@PathVariable("deckId") Long deckId, @PathVariable("cardId") Long cardId, Model model) {
        CardDeckCreateUpdateDto cardDeckCreateUpdateDto = new CardDeckCreateUpdateDto("new", cardId, deckId);
        DeckReadDto deckReadDto = deckService.findById(deckId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var cardDeckReadDto = cardDeckService.create(cardDeckCreateUpdateDto);
        model.addAttribute("cardadd", cardDeckReadDto);
        model.addAttribute("deck", deckReadDto);
        return "redirect:/decks/{deckId}";
    }

    @PostMapping("/{deckId}/{cardId}/delete")
    public String removeCardFromDeck(@PathVariable("deckId") Long deckId, @PathVariable("cardId") Long cardId, Model model) {
        cardDeckService.delete(deckId, cardId);
        return "redirect:/decks/{deckId}";
    }
}
