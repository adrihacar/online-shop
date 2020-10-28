DROP TABLE CARTPRODUCTS;
DROP TABLE CARTS;
DROP TABLE PRODUCTS;
DROP TABLE USERS;

CREATE TABLE USERS (
    id SERIAL PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    surname VARCHAR(256) NOT NULL,
    email VARCHAR(256) UNIQUE NOT NULL,
    location VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL
);

CREATE TABLE PRODUCTS (
    id SERIAL PRIMARY KEY,
    seller BIGINT UNSIGNED NOT NULL,
    name VARCHAR(256) NOT NULL,
    status INTEGER NOT NULL,
    category INTEGER NOT NULL,
    description TEXT NOT NULL,
    image BLOB,
    price DOUBLE NOT NULL,
    
    FOREIGN KEY(seller) REFERENCES USERS(id)
);

CREATE TABLE CARTS (
    id SERIAL PRIMARY KEY,
    user BIGINT UNSIGNED NOT NULL,
    address VARCHAR(256) NOT NULL,
    paymethod INTEGER NOT NULL,
    bought BOOLEAN NOT NULL,
    date BIGINT NOT NULL,

    FOREIGN KEY(user) REFERENCES USERS(id)
);

CREATE TABLE CARTPRODUCTS (
    id SERIAL PRIMARY KEY,
    cart BIGINT UNSIGNED NOT NULL,
    product BIGINT UNSIGNED NOT NULL,
    quantity INTEGER NOT NULL,

    FOREIGN KEY(cart) REFERENCES CARTS(id),
    FOREIGN KEY(product) REFERENCES PRODUCTS(id)
);

INSERT INTO USERS (name, surname, email, location, password) VALUES
  ("John","Doe","email1@gmail.com","madrid","asdfaszd"),
  ("Jane","Doe","email2@gmail.com","madrid","asdfaszd"),
  ("Fulanito","Doe","email3@gmail.com","madrid","asdfaszd"),
  ("Fulanita","Doe","email4gmail.com","madrid","asdfaszd");
  
INSERT INTO PRODUCTS (seller, name, status, category, description, price) VALUES
  (1,"Paper",0,8,"asdfaszd",4.9),
  (1,"keyboard",0,6,"asdfaszd",2.7),
  (3,"Pencil",0,9,"asdfaszd",0.7),
  (2,"Gums",0,1,"asdfaszd",2.2);
  
INSERT INTO CARTS (user, address, paymethod, bought, date) VALUES
  (2,"Mordor",1,true,12312381);
  
INSERT INTO CARTPRODUCTS (cart, product, quantity) VALUES
  (1,3,2),
  (1,1,1);

