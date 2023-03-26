package com.cloakyloki.dao;

import com.cloakyloki.integration.annotation.IT;
import com.cloakyloki.util.TestDataProvider;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@IT
@Transactional
@RequiredArgsConstructor
class ManacostRepositoryIT {

    private final ManacostRepository manacostRepository;
    private final CardRepository cardRepository;


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