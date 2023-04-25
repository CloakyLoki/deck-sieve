package com.cloakyloki.integration.controller;

import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import static com.cloakyloki.dto.UserCreateUpdateDto.Fields.isActive;
import static com.cloakyloki.dto.UserCreateUpdateDto.Fields.rawPassword;
import static com.cloakyloki.dto.UserCreateUpdateDto.Fields.role;
import static com.cloakyloki.dto.UserCreateUpdateDto.Fields.username;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RequiredArgsConstructor
class UserControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final UserService userService;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("userview/users"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void findById() throws Exception {
        var userReadDto = userService.create(new UserCreateUpdateDto(
                "test",
                "123",
                Role.USER,
                true
        ));
        mockMvc.perform(get("/users/" + userReadDto.getId().toString()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", Matchers.equalTo(userReadDto)))
                .andExpect(view().name("userview/user"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .with(csrf())
                        .param(username, "testNick")
                        .param(rawPassword, "123")
                        .param(role, "USER")
                        .param(isActive, "true")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login")
                );
    }

    @Test
    void update() throws Exception {
        var userReadDto = userService.create(new UserCreateUpdateDto(
                "test",
                "123",
                Role.USER,
                true
        ));
        var userId = userReadDto.getId().toString();
        mockMvc.perform(post("/users/" + userId + "/update")
                        .with(csrf())
                        .param(username, "Andrey")
                        .param(rawPassword, "111")
                        .param(role, "ADMIN")
                        .param(isActive, "true")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users/" + userId)
                );
    }

    @Test
    void delete() throws Exception {
        var userReadDto = userService.create(new UserCreateUpdateDto(
                "test",
                "123",
                Role.USER,
                true
        ));
        var userId = userReadDto.getId().toString();

        mockMvc.perform(post("/users/" + userId + "/delete")
                        .with(csrf())
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );
    }
}