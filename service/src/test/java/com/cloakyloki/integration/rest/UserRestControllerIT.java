package com.cloakyloki.integration.rest;

import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
class UserRestControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Test
    void findById() throws Exception {
        var userReadDto = userService.create(new UserCreateUpdateDto(
                "test",
                "123",
                Role.USER,
                true
        ));
        mockMvc.perform(get("/api/v1/users/" + userReadDto.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("test"));
    }

    @Test
    void create() throws Exception {
        var newUser = new UserCreateUpdateDto(
                "test",
                "123",
                Role.USER,
                true
        );

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.username").value(newUser.getUsername())
                );
    }

    @Test
    void update() throws Exception {
        var newUser = userService.create(new UserCreateUpdateDto(
                "test",
                "123",
                Role.USER,
                true
        ));
        var updatedUser = new UserCreateUpdateDto(
                "AAA",
                "777",
                Role.ADMIN,
                true
        );
        var userId = newUser.getId().toString();

        mockMvc.perform(put("/api/v1/users/" + userId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser))
                )
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.username").value(updatedUser.getUsername())
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
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/users/" + userReadDto.getId())
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }
}