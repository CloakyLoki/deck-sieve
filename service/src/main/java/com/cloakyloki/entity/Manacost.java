package com.cloakyloki.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "card")
@Builder
@Entity
@Table(name = "card_manacost_qty")
public class Manacost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Card card;
    private Integer red;
    private Integer black;
    private Integer green;
    private Integer blue;
    private Integer white;
    private Integer common;

    public void setCard(Card card) {
        card.setManacost(this);
        this.card = card;
    }
}