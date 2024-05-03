--liquibase formatted sql

--changeset hackathon:1
CREATE TABLE District (
  district_id BIGSERIAL PRIMARY KEY,
  district_name varchar(255)
);

CREATE TABLE City (
  city_id BIGSERIAL PRIMARY KEY,
  city_name varchar(255) NOT NULL
);

CREATE TABLE Users (
    user_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    organization VARCHAR(255) NULL,
    is_banned BOOLEAN DEFAULT FALSE
);

CREATE TABLE Status (
    status_id BIGSERIAL PRIMARY KEY,
    status_name VARCHAR(50) NOT NULL
);

CREATE TABLE Card (
    card_id BIGSERIAL PRIMARY KEY,
    complexity INT ,
    comment VARCHAR(255),
    photo TEXT,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    points INT,
    city_id INT REFERENCES City(city_id),
    district_id INT REFERENCES District(district_id),
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE Cleaning (
    cleaning_id BIGSERIAL PRIMARY KEY,
    card_id INT REFERENCES Card(card_id) NOT NULL,
    status_id INT REFERENCES Status(status_id),
    user_id INT REFERENCES Users(user_id) NULL,
    time TIMESTAMP
);

-- Заполнение таблицы статусов
INSERT INTO Status (status_name) VALUES
     ('created'),
     ('in process'),
     ('being reviewed'),
     ('closed');

--rollback drop table User, Status, Card, Cleaning;
