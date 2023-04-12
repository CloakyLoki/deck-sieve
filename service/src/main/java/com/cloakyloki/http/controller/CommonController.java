package com.cloakyloki.http.controller;

import com.cloakyloki.entity.enumerated.CardSubType;
import com.cloakyloki.entity.enumerated.CardSuperType;
import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class CommonController {

    @GetMapping("/index")
    public String indexPage(Model model) {

        model.addAttribute("rarity", Rarity.values());
        model.addAttribute("type", CardType.values());
        model.addAttribute("subtype", CardSubType.values());
        model.addAttribute("supertype", CardSuperType.values());
        model.addAttribute("subtype", CardSubType.values());
        model.addAttribute("isBanned", false);

        return "commonview/index";
    }
}
