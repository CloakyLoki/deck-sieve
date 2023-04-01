package com.cloakyloki.dao.repository;

import com.cloakyloki.dao.repository.filter.FilterCardRepository;
import com.cloakyloki.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long>, FilterCardRepository {

}
