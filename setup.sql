set foreign_key_checks = 0;
drop table if exists Customer;
drop table if exists COrder;
drop table if exists Pallet;
drop table if exists Cookie;
drop table if exists Ingredient;
drop table if exists CookieInOrder;
drop table if exists Recipe;
set foreign_key_checks = 1;

#Class relations:
CREATE TABLE Customer(
customerId INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30),
address VARCHAR(50)
);

CREATE TABLE Cookie(
cookieType VARCHAR(30) PRIMARY KEY
);

CREATE TABLE COrder(
orderId INT PRIMARY KEY AUTO_INCREMENT,
deliveryDate DATE,
customerId INT,
FOREIGN KEY (customerId) REFERENCES Customer(customerId)
);

CREATE TABLE Pallet(
palletId INT PRIMARY KEY AUTO_INCREMENT,
date DATE,
time TIME,
orderId INT,
location INT,
dateDelivered DATE,
cookieType VARCHAR(30),
status BOOLEAN,
FOREIGN KEY (cookieType) REFERENCES Cookie(cookieType),
FOREIGN KEY (orderId) REFERENCES COrder(orderId)
);

CREATE TABLE Ingredient(
ingredientName VARCHAR(30) PRIMARY KEY,
amountInStock INT,
latestDelivery DATE,
latestAmountDelivered INT
);

# Relations from associations:
CREATE TABLE CookieInOrder(
orderId INT,
cookieType VARCHAR(30),
PRIMARY KEY (orderId, cookieType),
FOREIGN KEY (orderId) REFERENCES COrder(orderId),
FOREIGN KEY (cookieType) REFERENCES Cookie(cookieType)
);

CREATE TABLE Recipe(
cookieType VARCHAR(30),
ingredientName VARCHAR(30),
amount INT,
PRIMARY KEY (cookieType, ingredientName),
FOREIGN KEY (cookieType) REFERENCES Cookie(cookieType),
FOREIGN KEY (ingredientName) REFERENCES Ingredient(ingredientName)
);


# Insert Customers:
INSERT INTO Customer(name, address) values('Kakmonster AB', 'Kakmonstergatan 1');

# Insert Cookies:
INSERT INTO Cookie values('Monsterkaka');

# Insert Orders:
INSERT INTO COrder(deliveryDate, customerId) values('3500-01-01', 1);

# Insert Pallet:
INSERT INTO Pallet(date, time, orderId, location, dateDelivered, cookieType, status) values('1500-01-01','23:59',null,0,null,'Monsterkaka',0);

# Insert Ingredient:
INSERT INTO Ingredient values('Monsterdelar',120,'1100-07-03',500);

# Insert CookieInOrder:
INSERT INTO CookieInOrder values(1,'Monsterkaka');

# Insert Recipe:
INSERT INTO Recipe values('Monsterkaka','Monsterdelar', 100);
