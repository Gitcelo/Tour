CREATE TABLE Tours (
    tourId INTEGER PRIMARY KEY,
    tourName varchar(128) NOT NULL,
    price int NOT NULL,
    description varchar(2000),
    difficulty int NOT NULL,
    providerName varchar(128)
);

CREATE TABLE Dates (
    tourId int NOT NULL,
    tourDate date NOT NULL,
    availableSeats int NOT NULL,
    PRIMARY KEY(tourId, tourDate),
    FOREIGN KEY(tourId) REFERENCES Tours(tourId)
);

CREATE TABLE Customers (
    name varchar(128),
    email varchar(128),
    primary key(name, email)
);

CREATE TABLE Reservations(
    tourId int NOT NULL,
    tourDate date NOT NULL,
    noOfSeats int NOT NULL,
    customerName varchar(128) NOT NULL,
    customerEmail varchar(128) NOT NULL,
    FOREIGN KEY(tourId, tourDate) REFERENCES Dates(tourId, tourDate),
    FOREIGN KEY(customerName, customerEmail) REFERENCES Customers(name, email)
);

--Database created--