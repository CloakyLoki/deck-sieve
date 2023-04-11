package com.cloakyloki.http.controller;

import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.dto.UserReadDto;
import com.cloakyloki.entity.enumerated.Role;
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

@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userView/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        UserReadDto userReadDto = userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("user", userReadDto);
        model.addAttribute("roles", Role.values());
        return "userView/user";

//        return userService.findById(id)
//                .map(user -> {
//                    model.addAttribute("user", user);
//                    model.addAttribute("roles", Role.values());
//                    return "user/user";
//                })
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(UserCreateUpdateDto user) {
        return "redirect:/users/" + userService.create(user).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute UserCreateUpdateDto user) {
        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }
}