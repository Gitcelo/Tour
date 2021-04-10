CREATE TABLE IF NOT EXISTS Tours (
    tourId serial primary key,
    tourName varchar(128) NOT NULL,
    price int NOT NULL,
    description varchar(2000),
    difficulty int NOT NULL,
    providerName varchar(128)
);

CREATE TABLE IF NOT EXISTS Dates (
    tourId int NOT NULL,
    tourDate date NOT NULL,
    availableSeats int NOT NULL,
    PRIMARY KEY(tourId, tourDate),
    FOREIGN KEY(tourId) REFERENCES Tours(tourId)
);

CREATE TABLE IF NOT EXISTS Reservations(
    tourId int NOT NULL,
    tourDate date NOT NULL,
    noOfSeats int NOT NULL,
    customerName varchar(128) NOT NULL,
    customerEmail varchar(128) NOT NULL,
    FOREIGN KEY(tourId, tourDate) REFERENCES Dates(tourId, tourDate),
    FOREIGN KEY(customerName, customerEmail) REFERENCES Customers(name, email)
);

CREATE TABLE IF NOT EXISTS Customers (
    name varchar(128),
    email varchar(128),
    primary key(name, email)
);