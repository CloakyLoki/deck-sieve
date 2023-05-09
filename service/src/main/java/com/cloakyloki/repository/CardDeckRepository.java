package com.cloakyloki.repository;

import com.cloakyloki.entity.CardDeck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface CardDeckRepository extends JpaRepository<CardDeck, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT cd FROM CardDeck cd WHERE cd.card.id = :cardId AND cd.deck.id = :deckId")
    List<CardDeck> findByDeckIdAndCardId(@Param("deckId") Long deckId, @Param("cardId") Long cardId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT cd FROM CardDeck cd WHERE cd.deck.id = :deckId")
    CardDeck findByDeckId(Long deckId);
}