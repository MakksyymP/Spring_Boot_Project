CREATE TABLE Persons
(
    id serial PRIMARY KEY,
    fullname VARCHAR(50) NOT NULL,
    birthyear INT
);

CREATE TABLE Books
(
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    author VARCHAR(50) NOT NULL,
    issueyear INT,
    order_time TIMESTAMPTZ,
    person_id INT REFERENCES Persons(id)
);

INSERT INTO Persons (fullname, birthyear)
VALUES ('John Doe', 1980),
       ('Jane Smith', 1995),
       ('Bob Johnson', 1978);

INSERT INTO Books (name, author, issueyear, order_time, person_id)
VALUES ('Book1', 'Author1', 2000, '2023-10-30 10:00:00', 1),
       ('Book2', 'Author2', 2010, '2023-10-30 11:00:00', 2),
       ('Book3', 'Author3', 1995, '2023-10-30 12:00:00', 3);
