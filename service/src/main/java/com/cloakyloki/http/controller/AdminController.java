package com.cloakyloki.http.controller;

import com.cloakyloki.dto.DeckReadDto;
import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.dto.UserReadDto;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.service.DeckService;
import com.cloakyloki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final DeckService deckService;

    @GetMapping("/users")
    public String adminPage(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userview/admin/users";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute UserCreateUpdateDto user, Model model) {
        model.addAttribute("user", userService.findByUsername(user.getUsername()));
        return userService.update(id, user)
                .map(it -> "redirect:/admin/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/users/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        UserReadDto userReadDto = userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<DeckReadDto> deckReadDtoList = deckService.findAllByUserId(id);
        model.addAttribute("user", userReadDto);
        model.addAttribute("roles", Role.values());
        model.addAttribute("decks", deckReadDtoList);
        return "userview/admin/edituser";
    }
}
