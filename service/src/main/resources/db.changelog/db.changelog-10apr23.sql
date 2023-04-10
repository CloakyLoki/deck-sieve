--liquibase formatted sql

--changeset cloakyloki:1
ALTER TABLE users
ALTER COLUMN password DROP NOT NULL;

--changeset cloakyloki:2
ALTER TABLE users
ALTER COLUMN active DROP NOT NULL;