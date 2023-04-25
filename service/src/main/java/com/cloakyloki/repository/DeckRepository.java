package com.cloakyloki.repository;

import com.cloakyloki.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    @Query("SELECT d FROM Deck d JOIN d.user u " +
            "WHERE d.user.id = :userId")
    List<Deck> findAllByUserId(@Param("userId") Long userId);
}