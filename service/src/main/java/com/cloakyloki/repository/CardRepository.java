package com.cloakyloki.repository;

import com.cloakyloki.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long>, QuerydslPredicateExecutor<Card>,
        FilterCardRepository {

    @Query("SELECT c FROM Card c JOIN c.cardDecks cd " +
            "WHERE cd.deck.id = :deckId")
    List<Card> findAllByDeckId(@Param("deckId") Long deckId);
}