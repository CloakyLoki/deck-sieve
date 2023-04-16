package com.cloakyloki.integration.controller;

import com.cloakyloki.dto.CardCreateUpdateDto;
import com.cloakyloki.entity.enumerated.CardSubType;
import com.cloakyloki.entity.enumerated.CardSuperType;
import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.service.CardService;
import com.cloakyloki.util.TestDataProvider;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.artist;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.flavorText;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.frameVersion;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.isBanned;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.keywords;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.manaValue;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.manacost;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.mvid;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.name;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.power;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.purchaseUrl;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.rarity;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.scryfallIllustrationId;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.subtype;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.supertype;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.text;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.toughness;
import static com.cloakyloki.dto.CardCreateUpdateDto.Fields.type;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RequiredArgsConstructor
@AutoConfigureMockMvc
class CardControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private final CardService cardService;

    @Test
    void findById() throws Exception {
        var cardReadDto = cardService.create(new CardCreateUpdateDto(
                "Mirage Mirror",
                3, null, null, null, null, null,
                null, null, null, null, null, null,
                null, "12", null, null, null));
        mockMvc.perform(get("/cards/" + cardReadDto.getId().toString()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("card"))
                .andExpect(model().attribute("card", Matchers.equalTo(cardReadDto)))
                .andExpect(view().name("cardview/card"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/cards")
                        .param(name, "testName")
                        .param(manaValue, "3")
                        .param(manacost, "testManaCost")
                        .param(rarity, Rarity.COMMON.toString())
                        .param(type, CardType.DRAGON.toString())
                        .param(subtype, CardSubType.ALIEN.toString())
                        .param(supertype, CardSuperType.LEGENDARY.toString())
                        .param(text, "testText")
                        .param(flavorText, "testFlavorText")
                        .param(keywords, "testKeywords")
                        .param(power, "5")
                        .param(artist, "testArtist")
                        .param(toughness, "7")
                        .param(purchaseUrl, "testUrl")
                        .param(mvid, "testMvId")
                        .param(scryfallIllustrationId, "testIllId")
                        .param(frameVersion, "2020")
                        .param(isBanned, Boolean.FALSE.toString())
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/cards/{\\d+}")
                );
    }

    @Test
    void update() throws Exception {
        var card = cardService.create(TestDataProvider.createCardUpdateDto());
        var cardId = card.getId().toString();
        mockMvc.perform(post("/cards/" + cardId + "/update")
                        .param(name, "NewCardName"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/cards/" + cardId)
                );
    }

    @Test
    void delete() throws Exception {
        var card = cardService.create(TestDataProvider.createCardUpdateDto());
        var cardId = card.getId().toString();
        mockMvc.perform(post("/cards/" + cardId + "/delete"))
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/cards")
                );
    }
}