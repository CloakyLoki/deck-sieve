package com.cloakyloki.http.controller;

import com.cloakyloki.dto.CardFilter;
import com.cloakyloki.dto.CustomUser;
import com.cloakyloki.dto.CustomUserDetails;
import com.cloakyloki.dto.PageResponse;
import com.cloakyloki.service.CardService;
import com.cloakyloki.service.DeckService;
import com.cloakyloki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@SessionAttributes("cardfilter")
@Controller
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;
    private final DeckService deckService;
    private final UserService userService;

    @GetMapping
    public String findAll(Model model, CardFilter filter, Pageable pageable) {
        var page = cardService.findAll(filter, pageable);
        model.addAttribute("cards", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "cardview/cards";
    }
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        var maybeUser = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> (CustomUserDetails) authentication.getPrincipal())
                .map(CustomUserDetails::getUsername)
                .orElseThrow();
        var customUser = (CustomUser) userService.loadUserByUsername(maybeUser);
        var decks = deckService.findAllByUserId(customUser.getId());
        return cardService.findById(id)
                .map(card -> {
                    model.addAttribute("card", card);
                    model.addAttribute("decks", decks);
                    return "cardview/card";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
