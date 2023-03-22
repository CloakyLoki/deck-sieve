package com.cloakyloki.dao;

import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.util.TestDataProvider;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ManacostRepositoryIT extends IntegrationTestBase {

    private final ManacostRepository manacostRepository = context.getBean(ManacostRepository.class);
    private final CardRepository cardRepository = context.getBean(CardRepository.class);


    @Test
    void deleteManacost() {
        var mishraCard = TestDataProvider.createMishraCard();
        var manacost = TestDataProvider.createTestManacost(mishraCard);
        cardRepository.save(mishraCard);
        manacostRepository.save(manacost);

        manacostRepository.delete(manacost);

        assertThat(manacostRepository.findById(manacost.getId())).isEmpty();
    }

    @Test
    void updateManacost() {
        var mishraCard = TestDataProvider.createMishraCard();
        var manacost = TestDataProvider.createTestManacost(mishraCard);
        cardRepository.save(mishraCard);
        manacostRepository.save(manacost);
        manacostRepository.getEntityManager().clear();

        manacost.setBlack(999);
        manacostRepository.update(manacost);
        manacostRepository.getEntityManager().clear();

        assertThat(manacostRepository.findById(manacost.getId())).isEqualTo(Optional.of(manacost));
    }

    @Test
    void createManacost() {
        var mishraCard = TestDataProvider.createMishraCard();
        var manacost = TestDataProvider.createTestManacost(mishraCard);
        cardRepository.save(mishraCard);
        manacostRepository.save(manacost);
        manacostRepository.getEntityManager().clear();

        assertThat(manacostRepository.findById(manacost.getId())).isNotEmpty();
    }

    @Test
    void findManacostById() {
        var mishraCard = TestDataProvider.createMishraCard();
        var manacost = TestDataProvider.createTestManacost(mishraCard);
        cardRepository.save(mishraCard);
        manacostRepository.save(manacost);
        manacostRepository.getEntityManager().clear();

        assertThat(manacostRepository.findById(manacost.getId())).isEqualTo(Optional.of(manacost));
    }
}