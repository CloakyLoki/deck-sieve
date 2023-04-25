package com.cloakyloki.http.controller;

import com.cloakyloki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.findAll());
//
//        model.addAttribute("rarity", Rarity.values());
//        model.addAttribute("type", CardType.values());
//        model.addAttribute("subtype", CardSubType.values());
//        model.addAttribute("supertype", CardSuperType.values());
//        model.addAttribute("subtype", CardSubType.values());
//        model.addAttribute("isBanned", false);

        return "commonview/admin";
    }
}
