package com.cloakyloki.repository;

import com.cloakyloki.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CardRepository extends JpaRepository<Card, Long>, QuerydslPredicateExecutor<Card> {
}