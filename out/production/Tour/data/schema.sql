CREATE TABLE Tours (
    tourId INTEGER PRIMARY KEY,
    tourName varchar(128) NOT NULL,
    price int NOT NULL,
    description varchar(2000),
    difficulty int NOT NULL,
    location varchar(128) NOT NULL,
    childFriendly BOOLEAN NOT NULL CHECK(childFriendly IN (0,1)),
    season SEASON NOT NULL CHECK(season IN (1,2,3,4)),
    providerName varchar(128)
);

CREATE TABLE Dates (
    tourId int NOT NULL,
    tourDate char(13) NOT NULL,
    availableSeats int NOT NULL,
    maxAvailableSeats int NOT NULL,
    PRIMARY KEY(tourId, tourDate),
    FOREIGN KEY(tourId) REFERENCES Tours(tourId)
);

CREATE TABLE Reservations(
    reservationId UNIQUE NOT NULL,
    tourId int NOT NULL,
    tourDate char(13) NOT NULL,
    noOfSeats int NOT NULL,
    customerName varchar(128) NOT NULL,
    customerEmail varchar(128) NOT NULL,
    PRIMARY KEY(tourId, tourDate, customerEmail),
    FOREIGN KEY(tourId, tourDate) REFERENCES Dates(tourId, tourDate)
);

--Database created--