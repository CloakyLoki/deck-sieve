package com.cloakyloki.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "card_deck")
@Entity
public class CardDeck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Card card;

    @ManyToOne
    private Deck deck;

    public void setCard(Card card) {
        this.card = card;
        this.card.getCardDecks().add(this);
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
        this.deck.getCardDecks().add(this);
    }
}