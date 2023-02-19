-- CREATE DATABASE mtg;
CREATE TABLE IF NOT EXISTS cards
(
    id                       SERIAL PRIMARY KEY,
    name                     VARCHAR(128),
    mana_value               INT,
    types                    VARCHAR(256),
    subtypes                 VARCHAR(256),
    supertypes               VARCHAR(256),
    rarity                   VARCHAR(64),
    text                     VARCHAR(512),
    flavor_text              VARCHAR(512),
    keywords                 VARCHAR(256),
    power                    INT,
    toughness                INT,
    purchase_url             VARCHAR(256),
    scryfall_illustration_id VARCHAR(256),
    is_banned                BOOLEAN
);

-- Colors need to cast a card
CREATE TABLE IF NOT EXISTS cards_manacost_qty
(
    card_id    INT REFERENCES cards (id),
    color_name VARCHAR(16),
    color_qty  INT
);

CREATE TABLE IF NOT EXISTS users
(
    id        SERIAL PRIMARY KEY,
    nickname  VARCHAR(128),
    password  VARCHAR(128),
    role      VARCHAR(64),
    is_active BOOLEAN
);

CREATE TABLE IF NOT EXISTS decks
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(128),
    user_id INT REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS favourites_decks
(
    user_id INT REFERENCES users (id),
    deck_id INT REFERENCES decks (id),
    PRIMARY KEY (user_id, deck_id)
);

CREATE TABLE IF NOT EXISTS deck_cards
(
    deck_id INT REFERENCES decks (id),
    card_id INT REFERENCES cards (id),
    PRIMARY KEY (card_id, deck_id)
);