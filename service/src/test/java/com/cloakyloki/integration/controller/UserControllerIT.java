package com.cloakyloki.integration.controller;

import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.cloakyloki.dto.UserCreateUpdateDto.Fields.isActive;
import static com.cloakyloki.dto.UserCreateUpdateDto.Fields.nickname;
import static com.cloakyloki.dto.UserCreateUpdateDto.Fields.password;
import static com.cloakyloki.dto.UserCreateUpdateDto.Fields.role;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RequiredArgsConstructor
@AutoConfigureMockMvc
class UserControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    final UserService userService;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
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
        mockMvc.perform(get("/users").param("id", userReadDto.getId().toString()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/users")
                        .param(nickname, "testNick")
                        .param(password, "123")
                        .param(role, "USER")
                        .param(isActive, "true"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
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
                        .param("nickname", "Andrey")
                        .param(password, "111")
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

        mockMvc.perform(post("/users/" + userId + "/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );
    }
}