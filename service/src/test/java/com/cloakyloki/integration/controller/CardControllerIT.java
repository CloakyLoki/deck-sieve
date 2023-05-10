package com.cloakyloki.integration.controller;

import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.test.web.servlet.MockMvc;

@RequiredArgsConstructor
class CardControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final CardService cardService;

//    @Test
//    void findById() throws Exception {
//        var cardReadDto = cardService.create(TestDataProvider.createCardUpdateDto());
//        var cardId = cardReadDto.getId().toString();
//        mockMvc.perform(get("/cards/" + cardId))
//                .andExpect(status().is2xxSuccessful())
//                .andExpect(model().attributeExists("card"))
//                .andExpect(model().attribute("card", Matchers.equalTo(cardReadDto)))
//                .andExpect(view().name("cardview/card"));
//    }
}