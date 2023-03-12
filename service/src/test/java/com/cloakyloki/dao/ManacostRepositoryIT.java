package com.cloakyloki.dao;

import com.cloakyloki.entity.Manacost;
import com.cloakyloki.integration.IntegrationTestBase;
import com.cloakyloki.util.TestDataProvider;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ManacostRepositoryIT extends IntegrationTestBase {

    ManacostRepository manacostRepository = new ManacostRepository(Manacost.class, session);

    @Test
    void deleteManacost() {
        var mishraCard = TestDataProvider.createMishraCard();
        var manacost = TestDataProvider.createTestManacost(mishraCard);
        session.save(mishraCard);
        manacostRepository.saveEntity(manacost);

        manacostRepository.deleteEntity(manacost);

        assertThat(session.get(Manacost.class, manacost.getId())).isNull();
    }

    @Test
    void updateManacost() {
        var mishraCard = TestDataProvider.createMishraCard();
        var manacost = TestDataProvider.createTestManacost(mishraCard);
        session.save(mishraCard);
        manacostRepository.saveEntity(manacost);
        session.clear();

        manacost.setBlack(999);
        manacostRepository.updateEntity(manacost);
        session.clear();

        assertThat(session.get(Manacost.class, manacost.getId())).isEqualTo(manacost);
    }

    @Test
    void createManacost() {
        var mishraCard = TestDataProvider.createMishraCard();
        var manacost = TestDataProvider.createTestManacost(mishraCard);
        session.save(mishraCard);
        manacostRepository.saveEntity(manacost);
        session.clear();

        assertThat(session.get(Manacost.class, manacost.getId())).isNotNull();
    }

    @Test
    void findManacostById() {
        var mishraCard = TestDataProvider.createMishraCard();
        var manacost = TestDataProvider.createTestManacost(mishraCard);
        session.save(mishraCard);
        manacostRepository.saveEntity(manacost);
        session.clear();

        assertThat(manacostRepository.findById(manacost.getId())).isEqualTo(Optional.of(manacost));
    }
}