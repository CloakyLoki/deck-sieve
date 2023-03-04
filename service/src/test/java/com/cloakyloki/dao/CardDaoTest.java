package com.cloakyloki.dao;

import com.cloakyloki.entity.Card;
import com.cloakyloki.entity.Card_;
import com.cloakyloki.entity.Manacost;
import com.cloakyloki.entity.enumerated.CardType;
import com.cloakyloki.entity.enumerated.Rarity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardDaoTest {

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
    void getCardByNameCriteria() {
        var mirageMirror = Card.builder()
                .name("Mirage Mirror")
                .flavorText("Most mirrors show your reflection. Other show your potential")
                .isBanned(false)
                .keywords("mirror")
                .manaValue(3)
                .type(CardType.ARTIFACT)
                .purchaseUrl("https://www.tcgplayer.com/product/134930")
                .rarity(Rarity.RARE)
                .scryfallIllustrationId("1562793459")
                .text("Mirage Mirror becomes a copy of target artifact, creature, enchantment, " +
                        "or land until end of turn.")
                .build();
        var mishraArtificer = Card.builder()
                .name("Mishra, Artificer Prodigy")
                .flavorText("A sojourn through time gave dark inspiration to one gifted young mind.")
                .isBanned(false)
                .keywords("mishra")
                .manaValue(4)
                .type(CardType.CREATURE)
                .purchaseUrl("https://www.tcgplayer.com/product/14296")
                .rarity(Rarity.RARE)
                .scryfallIllustrationId("1562940254")
                .text("Whenever you cast an artifact spell, you may search your graveyard, hand, " +
                        "and/or library for a card with the same name as that spell and put it onto the battlefield. " +
                        "If you search your library this way, shuffle.")
                .build();
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
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Card.class);
        var card = criteria.from(Card.class);
        String requestedCardName = "Mishra, Artificer Prodigy";

        criteria.select(card).where(
                cb.equal(card.get(Card_.NAME), requestedCardName));
        var resultlist = session.createQuery(criteria)
                .list();

        assertThat(resultlist.size()).isEqualTo(1);
        assertThat(resultlist.get(0)).isEqualTo(mishraArtificer);
    }

    @Test
    void getCardByNameQueryDSL() {
        var mirageMirror = Card.builder()
                .name("Mirage Mirror")
                .flavorText("Most mirrors show your reflection. Other show your potential")
                .isBanned(false)
                .keywords("mirror")
                .manaValue(3)
                .type(CardType.ARTIFACT)
                .purchaseUrl("https://www.tcgplayer.com/product/134930")
                .rarity(Rarity.RARE)
                .scryfallIllustrationId("1562793459")
                .text("Mirage Mirror becomes a copy of target artifact, creature, enchantment, " +
                        "or land until end of turn.")
                .build();
        var mishraArtificer = Card.builder()
                .name("Mishra, Artificer Prodigy")
                .flavorText("A sojourn through time gave dark inspiration to one gifted young mind.")
                .isBanned(false)
                .keywords("mishra")
                .manaValue(4)
                .type(CardType.CREATURE)
                .purchaseUrl("https://www.tcgplayer.com/product/14296")
                .rarity(Rarity.RARE)
                .scryfallIllustrationId("1562940254")
                .text("Whenever you cast an artifact spell, you may search your graveyard, hand, " +
                        "and/or library for a card with the same name as that spell and put it onto the battlefield. " +
                        "If you search your library this way, shuffle.")
                .build();
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
                .list();

        assertThat(resultlist.size()).isEqualTo(1);
        assertThat(resultlist.get(0)).isEqualTo(mishraArtificer);
    }
}