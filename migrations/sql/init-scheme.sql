--liquibase formatted sql

--changeset hackathon:1
CREATE TYPE role AS ENUM ('user', 'admin');

CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role role NOT NULL,
    organization VARCHAR(255) NULL
);

CREATE TABLE Status (
    status_id SERIAL PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL
);

CREATE TABLE Card (
    card_id SERIAL PRIMARY KEY,
    complexity INT ,
    comment VARCHAR(255),
    photo TEXT,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    points INT,
    status_id INT REFERENCES Status(status_id)
);

CREATE TABLE Cleaning (
    cleaning_id SERIAL PRIMARY KEY,
    card_id INT REFERENCES Card(card_id),
    status_id INT REFERENCES Status(status_id),
    time TIMESTAMP
);

CREATE TABLE RequestStatusPerson (
     request_id SERIAL PRIMARY KEY,
     person_id INT REFERENCES Users(user_id)
);

-- Заполнение таблицы статусов
INSERT INTO Status (status_name) VALUES
     ('created'),
     ('in process'),
     ('being reviewed'),
     ('closed');

--rollback drop table User, Status, Card, Cleaning;
