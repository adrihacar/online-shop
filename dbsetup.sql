CREATE DATABASE IF NOT EXISTS `onlineShop`;
USE `onlineShop`;

DROP TABLE IF EXISTS cartproducts;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    surname VARCHAR(256) NOT NULL,
    email VARCHAR(256) UNIQUE NOT NULL,
    location VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL,
    salt TEXT NOT NULL
);

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    seller BIGINT UNSIGNED NOT NULL,
    name VARCHAR(256) NOT NULL,
    status INTEGER NOT NULL,
    category INTEGER NOT NULL,
    description TEXT NOT NULL,
    image BLOB,
    price DOUBLE NOT NULL,
    
    FOREIGN KEY(seller) REFERENCES users(id)
);

CREATE TABLE carts (
    id SERIAL PRIMARY KEY,
    user BIGINT UNSIGNED NOT NULL,
    address VARCHAR(256) NOT NULL,
    paymethod INTEGER NOT NULL,
    bought BOOLEAN NOT NULL,
    date BIGINT NOT NULL,

    FOREIGN KEY(user) REFERENCES users(id)
);

CREATE TABLE cartproducts (
    id SERIAL PRIMARY KEY,
    cart BIGINT UNSIGNED NOT NULL,
    product BIGINT UNSIGNED NOT NULL,
    quantity INTEGER NOT NULL,

    FOREIGN KEY(cart) REFERENCES carts(id),
    FOREIGN KEY(product) REFERENCES products(id)
);

INSERT INTO users (name, surname, email, location, password, salt) VALUES
  ("John","Doe","email1@gmail.com","madrid","asdfaszd","asdf"),
  ("Jane","Doe","email2@gmail.com","madrid","asdfaszd","asdf"),
  ("Fulanito","Doe","email3@gmail.com","madrid","asdfaszd","asdf"),
  ("Fulanita","Doe","email4gmail.com","madrid","asdfaszd","asdf");
  
INSERT INTO products (seller, name, status, category, description, price) VALUES
  (1,"Paper",0,8,"asdfaszd",4.9),
  (1,"keyboard",0,6,"asdfaszd",2.7),
  (3,"Pencil",0,9,"asdfaszd",0.7),
  (2,"Gums",0,1,"asdfaszd",2.2);
  
INSERT INTO carts (user, address, paymethod, bought, date) VALUES
  (2,"Mordor",1,true,12312381);
  
INSERT INTO cartproducts (cart, product, quantity) VALUES
  (1,3,2),
  (1,1,1);

