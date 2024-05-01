--liquibase formatted sql

--changeset hackathon:1
CREATE TABLE IF NOT EXISTS Users (
    user_id SERIAL PRIMARY KEY,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(25) NOT NULL
);

--rollback drop table Users;
