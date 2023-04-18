package com.cloakyloki.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "cardDecks")
@ToString(exclude = "cardDecks")
@Builder
@Entity
public class Card implements GenericEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "mana_value")
    private Integer manaValue;
    private String manacost;


    private String rarity;
    private String type;
    private String subtype;
    private String supertype;
    private String text;

    @Column(name = "flavor_text")
    private String flavorText;
    private String keywords;
    private String power;
    private String toughness;
    private String artist;

    @Column(name = "purchase_url")
    private String purchaseUrl;

    @Column(name = "mvid")
    private String mvid;

    @Column(name = "scryfall_illustration_id")
    private String scryfallIllustrationId;

    @Column(name = "frame_version")
    private String frameVersion;

    @Column(name = "banned")
    private Boolean isBanned;

    @Builder.Default
    @OneToMany(mappedBy = "card")
    private List<CardDeck> cardDecks = new ArrayList<>();
}