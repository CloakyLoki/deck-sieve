--liquibase formatted sql

--changeset cloakyloki:1
CREATE TABLE IF NOT EXISTS sets
(
    code             VARCHAR(8) PRIMARY KEY,
    basesetsize      INTEGER,
    block            TEXT,
    booster          TEXT,
    cardspheresetid  INTEGER,
    isfoilonly       INTEGER DEFAULT 0 NOT NULL,
    isforeignonly    INTEGER DEFAULT 0 NOT NULL,
    isnonfoilonly    INTEGER DEFAULT 0 NOT NULL,
    isonlineonly     INTEGER DEFAULT 0 NOT NULL,
    ispartialpreview INTEGER DEFAULT 0 NOT NULL,
    keyrunecode      TEXT,
    languages        TEXT,
    mtgocode         TEXT,
    name             TEXT,
    parentcode       TEXT,
    releasedate      DATE,
    sealedproduct    TEXT,
    tcgplayergroupid INTEGER,
    tokensetcode     TEXT,
    totalsetsize     INTEGER,
    type             TEXT
);

--changeset cloakyloki:2
CREATE TABLE IF NOT EXISTS card
(
    id                       BIGSERIAL PRIMARY KEY,
    name                     TEXT NOT NULL,
    mana_value               INT,
    manacost                 VARCHAR(128),
    rarity                   TEXT,
    type                     TEXT,
    subtype                  TEXT,
    supertype                TEXT,
    text                     TEXT,
    flavor_text              TEXT,
    keywords                 TEXT,
    power                    TEXT,
    artist                   TEXT,
    toughness                TEXT,
    purchase_url             TEXT,
    mvid                     TEXT,
    scryfall_illustration_id TEXT,
    frame_version            TEXT,
    banned                   BOOLEAN DEFAULT FALSE,
    setcode                  VARCHAR(8) REFERENCES sets(code)
);
--rollback DROP TABLE IF EXISTS card;

--changeset cloakyloki:3
CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    nickname VARCHAR(128) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    role     VARCHAR(64)  NOT NULL,
    active   BOOLEAN      NOT NULL
);
--rollback DROP TABLE IF EXISTS users;

--changeset cloakyloki:4
CREATE TABLE IF NOT EXISTS deck
(
    id        BIGSERIAL PRIMARY KEY,
    name      VARCHAR(128),
    favourite BOOLEAN                      NOT NULL,
    user_id   BIGINT REFERENCES users (id) NOT NULL
);
--rollback DROP TABLE IF EXISTS deck;

--changeset cloakyloki:5
CREATE TABLE IF NOT EXISTS card_deck
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(64),
    card_id BIGINT REFERENCES card (id) NOT NULL,
    deck_id BIGINT REFERENCES deck (id) NOT NULL
);
--rollback DROP TABLE IF EXISTS card_deck;