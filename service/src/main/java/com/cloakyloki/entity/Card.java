package com.cloakyloki.entity;
import com.cloakyloki.entity.enumerated.CardSubType;
import com.cloakyloki.entity.enumerated.CardSuperType;
import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "mana_value")
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

    @Column(name = "banned")
    private Boolean isBanned;
}