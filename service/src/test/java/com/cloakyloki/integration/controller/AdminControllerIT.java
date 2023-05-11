package com.cloakyloki.integration.controller;

import com.cloakyloki.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.springframework.test.web.servlet.MockMvc;

@RequiredArgsConstructor
public class AdminControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;

//    @Test
//    void findAll() throws Exception {
//        mockMvc.perform(get("/admin/users"))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(view().name("userview/admin/users"))
//                .andExpect(model().attributeExists("users"));
//    }
}
