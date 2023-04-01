package com.cloakyloki.dao.repository;

import com.cloakyloki.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, Long> {
}
