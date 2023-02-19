package com.cloakyLoki.entity;

import com.cloakyLoki.entity.enumerated.CardSubType;
import com.cloakyLoki.entity.enumerated.CardSuperType;
import com.cloakyLoki.entity.enumerated.CardType;
import com.cloakyLoki.entity.enumerated.Rarity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer manaValue;

    @Enumerated(EnumType.STRING)
    private CardType type;

    @Enumerated(EnumType.STRING)
    private CardSubType subtype;

    @Enumerated(EnumType.STRING)
    private CardSuperType supertype;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;
    private String text;

    @Column(name = "flavor_text")
    private String flavorText;
    private String keywords;
    private Integer power;
    private Integer toughness;

    @Column(name = "purchase_url")
    private String purchaseUrl;

    @Column(name = "scryfall_illustration_id")
    private String scryfallIllustrationId;

    @Column(name = "is_banned")
    private Boolean isBanned;
}