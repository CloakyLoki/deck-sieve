package com.cloakyloki.repository;

import com.cloakyloki.entity.CardDeck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardDeckRepository extends JpaRepository<CardDeck, Long> {
}