package com.cloakyloki.http.controller;

import com.cloakyloki.dto.CardCreateUpdateDto;
import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @GetMapping
    public String findAll(Model model, CardFilter filter) {
        model.addAttribute("cards", cardService.findAll(filter));
        return "cardview/cards";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        return cardService.findById(id)
                .map(card -> {
                    model.addAttribute("card", card);
                    return "cardview/card";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(CardCreateUpdateDto card) {
        return "redirect:/cards/" + cardService.create(card).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute @Validated CardCreateUpdateDto card) {
        return cardService.update(id, card)
                .map(it -> "redirect:/cards/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!cardService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/cards";
    }
}
