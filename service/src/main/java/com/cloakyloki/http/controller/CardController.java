package com.cloakyloki.http.controller;

import com.cloakyloki.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cards")
public class CardController {

    CardService cardService;

    @GetMapping
    public String findAll(Model model) {
//        model.addAttribute("cards", cardService.findAll());
        return "card/cards";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("cards", cardService.findById(id));
        return "card/card";
    }


}
