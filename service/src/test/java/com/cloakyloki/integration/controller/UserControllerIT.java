package com.cloakyloki.integration.controller;

import com.cloakyloki.integration.IntegrationTestBase;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RequiredArgsConstructor
@AutoConfigureMockMvc
class UserControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void findById() {
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
    void update() {
    }

    @Test
    void delete() {
    }
}