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
location INT DEFAULT 0,
dateDelivered DATE,
cookieType VARCHAR(30) NOT null,
status BOOLEAN DEFAULT false,
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
amount INT,
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
INSERT INTO Customer(name, address) values('Zombiegott AB', 'levandedödgatan 10');
INSERT INTO Customer(name, address) values('Kakmonster AB', 'Kakmonstergatan 1');

# Insert Cookies:
INSERT INTO Cookie values('Monsterkaka');
INSERT INTO Cookie values('Zombiekaka');
INSERT INTO Cookie values('Ben-pinne');

# Insert Orders:
INSERT INTO COrder(deliveryDate, customerId) values('3500-01-01', 1);

# Insert Pallet:
INSERT INTO Pallet(date, time, orderId, dateDelivered, cookieType) values('2016-01-01','14:00',null,null,'Monsterkaka');
INSERT INTO Pallet(date, time, orderId, dateDelivered, cookieType) values('2016-02-01','15:00',null,null,'Zombiekaka');
INSERT INTO Pallet(date, time, orderId, dateDelivered, cookieType) values('2016-03-01','16:00',null,null,'Ben-pinne');
INSERT INTO Pallet(date, time, orderId, dateDelivered, cookieType) values('2016-04-01','17:00',null,null,'Monsterkaka');

# Insert Ingredient:
INSERT INTO Ingredient values('Monsterdelar',400,'2015-07-03',500);
INSERT INTO Ingredient values('Hjärna',200,'2015-10-20',200);
INSERT INTO Ingredient values('Ben',150,'2015-12-15',200);

# Insert CookieInOrder:
INSERT INTO CookieInOrder values(1,'Monsterkaka',5);

# Insert Recipe:
INSERT INTO Recipe values('Monsterkaka','Monsterdelar', 50);
INSERT INTO Recipe values('Zombiekaka','Hjärna', 10);
INSERT INTO Recipe values('Zombiekaka','Monsterdelar', 30);
INSERT INTO Recipe values('Ben-pinne','Ben', 60);
