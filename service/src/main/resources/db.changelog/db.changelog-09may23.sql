--liquibase formatted sql

--changeset cloakyloki:1
ALTER TABLE deck
ADD COLUMN IF NOT EXISTS version BIGINT DEFAULT 0;

--changeset cloakyloki:2
ALTER TABLE users
ADD COLUMN IF NOT EXISTS version BIGINT DEFAULT 0;

--changeset cloakyloki:3
ALTER TABLE card_deck
ADD COLUMN IF NOT EXISTS version BIGINT DEFAULT 0;


