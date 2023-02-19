-- CREATE DATABASE mtg;
CREATE TABLE IF NOT EXISTS cards
(
    id                       BIGSERIAL PRIMARY KEY,
    name                     VARCHAR(128) NOT NULL UNIQUE ,
    mana_value               INT,
    types                    VARCHAR(256) NOT NULL ,
    subtypes                 VARCHAR(256),
    supertypes               VARCHAR(256),
    rarity                   VARCHAR(64) NOT NULL ,
    text                     VARCHAR(512) NOT NULL ,
    flavor_text              VARCHAR(512),
    keywords                 VARCHAR(256) NOT NULL ,
    power                    INT,
    toughness                INT,
    purchase_url             VARCHAR(256),
    scryfall_illustration_id VARCHAR(256),
    is_banned                BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS cards_manacost_qty
(
    card_id BIGSERIAL REFERENCES cards (id),
    red     INT,
    black   INT,
    green   INT,
    blue    INT,
    white   INT,
    common  INT
);

CREATE TABLE IF NOT EXISTS users
(
    id        BIGSERIAL PRIMARY KEY,
    nickname  VARCHAR(128) NOT NULL UNIQUE ,
    password  VARCHAR(128) NOT NULL ,
    role      VARCHAR(64) NOT NULL ,
    is_active BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS decks
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(128) NOT NULL ,
    user_id BIGINT REFERENCES users (id) NOT NULL
);

CREATE TABLE IF NOT EXISTS favourites_decks
(
    user_id BIGINT REFERENCES users (id) NOT NULL ,
    deck_id BIGINT REFERENCES decks (id) NOT NULL ,
    PRIMARY KEY (user_id, deck_id)
);

CREATE TABLE IF NOT EXISTS deck_cards
(
    deck_id BIGINT REFERENCES decks (id) NOT NULL ,
    card_id BIGINT REFERENCES cards (id) NOT NULL ,
    PRIMARY KEY (card_id, deck_id)
);