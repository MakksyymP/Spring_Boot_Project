CREATE TABLE People
(
    id serial PRIMARY KEY,
    fullname VARCHAR(50) NOT NULL UNIQUE,
    birthyear INT
);

CREATE TABLE Books
(
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    author VARCHAR(50) NOT NULL,
    issueyear INT NOT NULL,
    order_time TIMESTAMPTZ,
    loan_duration_weeks INT,
    person_id INT REFERENCES People(id)
);

CREATE TABLE successful_returns (
    id INT PRIMARY KEY REFERENCES People(id),
    successful_returns INT,
    overdue_returns INT
);

INSERT INTO People (fullname, birthyear)
VALUES ('John Doe', 1980),
       ('Jane Smith', 1995),
       ('Bob Johnson', 1978);

INSERT INTO successful_returns (id, successful_returns, overdue_returns)
VALUES (1, 0, 0),
       (2, 0, 0),
       (3, 0, 0);


INSERT INTO Books (name, author, issueyear, order_time, loan_duration_weeks, person_id)
VALUES ('Book1', 'Author1', 2000, '2023-10-30 10:00:00', 1, 1),
       ('Book2', 'Author2', 2010, '2023-10-30 11:00:00', 1, 2),
       ('Book3', 'Author3', 1995, '2023-10-30 12:00:00', 1, 3);
