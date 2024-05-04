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

CREATE TABLE MaxStatus(
    card_id INT REFERENCES Card(card_id),
    max_status INT
);

CREATE TABLE Cleaning (
    cleaning_id BIGSERIAL PRIMARY KEY,
    card_id INT REFERENCES Card(card_id) NOT NULL,
    status_id INT REFERENCES Status(status_id),
    user_id INT REFERENCES Users(user_id) NULL,
    time timestamptz
);

-- Заполнение таблицы статусов
INSERT INTO Status (status_name) VALUES
     ('created'),
     ('in process'),
     ('being reviewed'),
     ('closed');

--changeset hackathon:2
INSERT INTO District (district_name) VALUES
     ('North District'),
     ('South District'),
     ('East District'),
     ('West District');

INSERT INTO City (city_name) VALUES
     ('Metropolis'),
     ('Gotham'),
     ('Springfield'),
     ('Riverdale');

INSERT INTO Users (first_name, last_name, email, password, role, organization) VALUES
   ('John', 'Doe', 'john.doe@example.com', 'password123', 'admin', 'Organization A'),
   ('Jane', 'Smith', 'jane.smith@example.com', 'password123', 'user', 'Organization B'),
   ('Jim', 'Beam', 'jim.beam@example.com', 'password123', 'user', NULL),
   ('Jack', 'Daniels', 'jack.daniels@example.com', 'password123', 'user', 'Organization C');

INSERT INTO Card (complexity, comment, photo, latitude, longitude, points, city_id, district_id) VALUES
     (5, 'Large area with lots of waste', 'http://example.com/photo1.jpg', 40.712776, -74.005974, 100, 1, 1),
     (3, 'Small cleanup needed', 'http://example.com/photo2.jpg', 34.052235, -118.243683, 50, 2, 2),
     (2, 'Moderate maintenance', 'http://example.com/photo3.jpg', 41.878113, -87.629799, 75, 3, 3),
     (4, 'Urgent attention needed', 'http://example.com/photo4.jpg', 37.774929, -122.419416, 150, 4, 4);

INSERT INTO MaxStatus(card_id, max_status) VALUES
     (1, 1),
     (2, 2),
     (3, 3),
     (4, 4);

INSERT INTO Cleaning (card_id, status_id, user_id, time) VALUES
     (1, 1, 1, '2023-01-01T10:00:00Z'),
     (2, 2, 2, '2023-01-02T11:00:00Z'),
     (3, 3, 3, '2023-01-03T12:00:00Z'),
     (4, 4, 4, '2023-01-04T13:00:00Z');


--rollback drop table User, Status, Card, Cleaning;
