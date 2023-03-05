package com.cloakyloki.dao;

import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.CardDeck;
import com.cloakyloki.entity.Card_;
import com.cloakyloki.entity.Deck;
import com.cloakyloki.entity.Manacost;
import com.cloakyloki.entity.User;
import com.cloakyloki.entity.enumerated.Role;
import com.cloakyloki.util.CardProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.graph.GraphSemantic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CardFilterIT {

    private Session session;
    static SessionFactory sessionFactory;

    @BeforeAll
    static void init() {
        var configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    @AfterAll
    static void close() {
        sessionFactory.close();
    }

    @BeforeEach
    void sessionInit() {
        this.session = sessionFactory.openSession();
        this.session.beginTransaction();
    }

    @AfterEach
    void cleanAfterTest() {
        this.session.getTransaction().rollback();
        this.session.close();
    }

    @Test
    @DisplayName("Get card by cardname using CriteriaAPI")
    void getCardByNameCriteria() {
        var mirageMirror = CardProvider.createMirrorCard();
        var mishraArtificer = CardProvider.createMishraCard();
        var mirrorManacost = Manacost.builder()
                .card(mirageMirror)
                .common(3)
                .build();
        var mishraManacost = Manacost.builder()
                .card(mishraArtificer)
                .blue(1)
                .red(1)
                .black(1)
                .common(1)
                .build();
        session.save(mirageMirror);
        session.save(mishraArtificer);
        session.save(mirrorManacost);
        session.save(mishraManacost);
        session.clear();
        var graph = session.createEntityGraph(Card.class);
        graph.addAttributeNodes("manacost");
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Card.class);
        var card = criteria.from(Card.class);
        String requestedCardName = "Mishra, Artificer Prodigy";

        criteria.select(card).where(
                cb.equal(card.get(Card_.NAME), requestedCardName));
        var resultlist = session.createQuery(criteria)
                .applyGraph(graph, GraphSemantic.FETCH)
                .list();

        assertThat(resultlist.size()).isEqualTo(1);
        assertThat(resultlist.get(0)).isEqualTo(mishraArtificer);
    }

    @Test
    @DisplayName("Get card by cardname using QueryDSL")
    void getCardByNameQueryDSL() {
        var mirageMirror = CardProvider.createMirrorCard();
        var mishraArtificer = CardProvider.createMishraCard();
        var mirrorManacost = Manacost.builder()
                .card(mirageMirror)
                .common(3)
                .build();
        var mishraManacost = Manacost.builder()
                .card(mishraArtificer)
                .blue(1)
                .red(1)
                .black(1)
                .common(1)
                .build();
        session.save(mirageMirror);
        session.save(mishraArtificer);
        session.save(mirrorManacost);
        session.save(mishraManacost);
        session.clear();
        String requestedCardName = "Mishra, Artificer Prodigy";

        var resultlist = session.createQuery("select c from Card c " +
                        "where c.name = :cardName", Card.class)
                .setParameter("cardName", requestedCardName)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), session.getEntityGraph("cardInfoWithManacost"))
                .list();

        assertThat(resultlist.size()).isEqualTo(1);
        assertThat(resultlist.get(0)).isEqualTo(mishraArtificer);
    }

    @Test
    @DisplayName("Get card by manacost using QueryDSL")
    void getCardByManacostQueryDSL() {
        var mirageMirror = CardProvider.createMirrorCard();
        var mishraArtificer = CardProvider.createMishraCard();
        var mirrorManacost = Manacost.builder()
                .card(mirageMirror)
                .common(3)
                .build();
        var mishraManacost = Manacost.builder()
                .card(mishraArtificer)
                .blue(1)
                .red(1)
                .black(1)
                .common(1)
                .build();
        var user = User.builder()
                .nickname("CloakyLoki")
                .password("777")
                .role(Role.USER)
                .isActive(true)
                .build();
        var deck = Deck.builder()
                .name("Arti deck")
                .favourite(true)
                .user(user)
                .build();
        var cardDeck = CardDeck.builder()
                .card(mishraArtificer)
                .deck(deck)
                .build();
        session.save(mirageMirror);
        session.save(mishraArtificer);
        session.save(mirrorManacost);
        session.save(mishraManacost);
        session.save(user);
        session.save(deck);
        session.save(cardDeck);
        session.clear();
        Integer redManaValue = 1;
        Integer blackManaValue = 1;
        Integer blueManaValue = 1;

        var resultlist = session.createQuery("select c from Card c " +
                        "where c.manacost.black = :blackmanavalue " +
                        "and c.manacost.blue = :bluemanavalue " +
                        "and c.manacost.red = :redmanavalue", Card.class)
                .setParameter("blackmanavalue", blackManaValue)
                .setParameter("bluemanavalue", blueManaValue)
                .setParameter("redmanavalue", redManaValue)
                .setHint(GraphSemantic.FETCH.getJpaHintName(), session.getEntityGraph("cardInfoWithManacost"))
                .list();

        assertThat(resultlist.size()).isEqualTo(1);
        assertThat(resultlist.get(0)).isEqualTo(mishraArtificer);
    }

    @Test
    @DisplayName("Get all decks containing card by cardname using QueryDSL")
    void getAllDecksWithCardQueryDSL() {
        var mishraArtificer = CardProvider.createMishraCard();
        var mishraManacost = Manacost.builder()
                .card(mishraArtificer)
                .blue(1)
                .red(1)
                .black(1)
                .common(1)
                .build();
        var user = User.builder()
                .nickname("CloakyLoki")
                .password("777")
                .role(Role.USER)
                .isActive(true)
                .build();
        var firstExpectedDeck = Deck.builder()
                .name("Arti deck")
                .favourite(true)
                .user(user)
                .build();
        var secondExpectedDeck = Deck.builder()
                .name("Recon deck")
                .favourite(false)
                .user(user)
                .build();
        var firstExpectedCardDeck = CardDeck.builder()
                .card(mishraArtificer)
                .deck(firstExpectedDeck)
                .build();
        var secondExpectedCardDeck = CardDeck.builder()
                .card(mishraArtificer)
                .deck(secondExpectedDeck)
                .build();
        session.save(mishraArtificer);
        session.save(mishraManacost);
        session.save(user);
        session.save(firstExpectedDeck);
        session.save(secondExpectedDeck);
        session.save(firstExpectedCardDeck);
        session.save(secondExpectedCardDeck);
        session.clear();
        String requestedCardName = "Mishra, Artificer Prodigy";

        var resultlist = session.createQuery("select d from Deck d " +
                        "join d.cardDecks cd " +
                        "join cd.card c " +
                        "where c.name = :requestedCard", Deck.class)
                .setParameter("requestedCard", requestedCardName)
                .list();

        assertAll(
                () -> assertThat(resultlist.size()).isEqualTo(2),
                () -> assertThat(resultlist.get(0)).isEqualTo(firstExpectedDeck),
                () -> assertThat(resultlist.get(1)).isEqualTo(secondExpectedDeck));
    }
}