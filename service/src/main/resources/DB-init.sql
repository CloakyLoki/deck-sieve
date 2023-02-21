CREATE TABLE IF NOT EXISTS card
(
    id                       BIGSERIAL PRIMARY KEY,
    name                     VARCHAR(128) NOT NULL UNIQUE,
    mana_value               INT,
    type                     VARCHAR(256) NOT NULL,
    subtype                  VARCHAR(256),
    supertype                VARCHAR(256),
    rarity                   VARCHAR(64)  NOT NULL,
    text                     VARCHAR(512) NOT NULL,
    flavor_text              VARCHAR(512),
    keywords                 VARCHAR(256) NOT NULL,
    power                    INT,
    toughness                INT,
    purchase_url             VARCHAR(256),
    scryfall_illustration_id VARCHAR(256),
    banned                   BOOLEAN      NOT NULL
);

-- Colors need to cast a card
CREATE TABLE IF NOT EXISTS card_manacost_qty
(
    id      BIGSERIAL,
    card_id BIGINT REFERENCES card (id),
    red     INT,
    black   INT,
    green   INT,
    blue    INT,
    white   INT,
    common  INT
);

CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    nickname VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    role     VARCHAR(64)  NOT NULL,
    active   BOOLEAN      NOT NULL
);

CREATE TABLE IF NOT EXISTS deck
(
    id        BIGSERIAL PRIMARY KEY,
    name      VARCHAR(128)                 NOT NULL,
    favourite BOOLEAN                      NOT NULL,
    user_id   BIGINT REFERENCES users (id) NOT NULL
);

CREATE TABLE IF NOT EXISTS deck_card
(
    deck_id BIGINT REFERENCES deck (id) NOT NULL,
    card_id BIGINT REFERENCES card (id) NOT NULL,
    PRIMARY KEY (card_id, deck_id)
);