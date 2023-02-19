package com.cloakyLoki.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    private Integer id;
    private String name;
    private Integer manaValue;
    private String type;
    private String subtype;
    private String supertype;
    private String rarity;
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