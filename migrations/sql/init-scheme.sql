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
    city_id BIGINT REFERENCES City(city_id),
    district_id BIGINT REFERENCES District(district_id),
    is_banned BOOLEAN DEFAULT FALSE,
    is_deleted BOOLEAN DEFAULT FALSE
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
    city_id BIGINT REFERENCES City(city_id),
    district_id BIGINT REFERENCES District(district_id),
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE MaxStatus
(
    card_id    INT REFERENCES Card (card_id),
    max_status BIGINT,
    max_count_of_proof INT
);

CREATE TABLE Cleaning (
    cleaning_id BIGSERIAL PRIMARY KEY,
    card_id INT REFERENCES Card(card_id) NOT NULL,
    status_id INT REFERENCES Status(status_id),
    user_id INT REFERENCES Users(user_id) NULL,
    time timestamptz,
    admin_proof boolean DEFAULT FALSE,
    count_of_proof INT DEFAULT 0
);

-- Заполнение таблицы статусов
INSERT INTO Status (status_name)
VALUES ('moderating'),
       ('created'),
       ('in process'),
       ('being reviewed'),
       ('closed');

--changeset hackathon:2
INSERT INTO District (district_name)
VALUES ('North District'),
       ('South District'),
       ('East District'),
       ('West District');

INSERT INTO City (city_name)
VALUES ('Metropolis'),
       ('Gotham'),
       ('Springfield'),
       ('Riverdale');

INSERT INTO Users (first_name, last_name, email, password, role, organization, city_id, district_id, is_banned)
VALUES ('John', 'Doe', 'john.doe@example.com', 'securepassword123', 'Admin', 'ExampleCorp', 1, 1, FALSE),
       ('Jane', 'Smith', 'jane.smith@example.com', 'securepassword456', 'User', 'OtherCorp', 2, 2, FALSE),
       ('Alice', 'Johnson', 'alice.johnson@example.com', 'securepassword789', 'Manager', NULL, 3, 3, FALSE),
       ('Bob', 'Brown', 'bob.brown@example.com', 'securepassword101', 'User', 'ExampleCorp', 4, 4, TRUE);

INSERT INTO Card (complexity, comment, photo, latitude, longitude, points, city_id, district_id)
VALUES (5, 'Large area with lots of waste', 'http://example.com/photo1.jpg', 51.522880, 46.020296, 100, 1, 1),
       (3, 'Small cleanup needed', 'http://example.com/photo2.jpg', 51.522873, 46.018126, 50, 2, 2),
       (2, 'Moderate maintenance', 'http://example.com/photo3.jpg', 51.522542, 46.020968, 75, 3, 3),
       (4, 'Urgent attention needed', 'http://example.com/photo4.jpg', 51.523477, 46.021631, 150, 4, 4),
       (2, 'Urgent attention needed2', 'http://example.com/photo44.jpg', 51.522415, 46.020017, 150, 4, 4);

INSERT INTO MaxStatus(card_id, max_status, max_count_of_proof)
VALUES (1, 1, 0),
       (2, 2, 0),
       (3, 3, 0),
       (4, 4, 0),
       (5, 5, 3);

INSERT INTO Cleaning (card_id, status_id, user_id, time, admin_proof, count_of_proof)
VALUES (1, 1, 1, '2023-01-01T10:00:00Z', false, 0),
       (2, 2, 2, '2023-01-02T11:00:00Z', true, 0),
       (3, 3, 3, '2023-01-03T12:00:00Z', true, 0),
       (4, 4, 4, '2023-01-04T13:00:00Z', true, 0),
       (5, 4, 1, '2023-01-04T13:00:00Z', true, 1),
       (5, 4, 2, '2023-01-04T13:00:00Z', true, 2),
       (5, 5, 3, '2023-01-04T13:00:00Z', true, 3);


--rollback drop table User, Status, Card, Cleaning;
