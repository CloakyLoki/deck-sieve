package com.cloakyloki.http.controller;

import com.cloakyloki.dto.CardReadDto;
import com.cloakyloki.dto.DeckReadDto;
import com.cloakyloki.service.CardService;
import com.cloakyloki.service.DeckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/decks")
public class DeckController {

    private final DeckService deckService;
    private final CardService cardService;

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        DeckReadDto deckReadDto = deckService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<CardReadDto> cardsInDeck = cardService.findAllByDeckId(id);
        model.addAttribute("deck", deckReadDto);
        model.addAttribute("cards", cardsInDeck);
        return "deckview/deck";
    }
}
