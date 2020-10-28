DROP TABLE USERS;
DROP TABLE PRODUCTS;
DROP TABLE CARTS;
DROP TABLE CARTITEMS;

CREATE TABLE USERS (
    id SERIAL PRIMARY KEY,
    name VARCHAR(256),
    surname VARCHAR(256),
    email VARCHAR(256) UNIQUE,
    location VARCHAR(256),
    password VARCHAR(256)
);

CREATE TABLE PRODUCTS (
    id SERIAL PRIMARY KEY,
    seller BIGINT UNSIGNED,
    name VARCHAR(256),
    status INTEGER,
    category INTEGER,
    description TEXT,
    image BLOB,
    price DOUBLE,
    
    FOREIGN KEY(seller) REFERENCES users(id)
);

CREATE TABLE CARTS (
    id SERIAL PRIMARY KEY,
    user BIGINT UNSIGNED,
    address VARCHAR(256),
    paymethod INTEGER,
    bought BOOLEAN,
    date VARCHAR(16),

    FOREIGN KEY(user) REFERENCES users(id)
);

CREATE TABLE CARTITEMS (
    id SERIAL PRIMARY KEY,
    cart BIGINT UNSIGNED,
    product BIGINT UNSIGNED,
    quantity INTEGER,

    FOREIGN KEY(cart) REFERENCES carts(id)
    FOREIGN KEY(product) REFERENCES products(id)
);
