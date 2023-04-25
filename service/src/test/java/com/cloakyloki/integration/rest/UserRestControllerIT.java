package com.cloakyloki.integration.rest;

import com.cloakyloki.dto.UserCreateUpdateDto;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@AutoConfigureMockMvc(addFilters = false)
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
                Role.USER,
                true
        );
        var userId = newUser.getId().toString();

        mockMvc.perform(put("/api/v1/users/" + userId)

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser))
                        .with(csrf()))
                .andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("$.username").value(updatedUser.getUsername()),
                        jsonPath("$.password").value(updatedUser.getRawPassword())
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
                        .delete("/api/v1/users/" + userReadDto.getId()))
                .andExpect(status().isNoContent());
    }
}