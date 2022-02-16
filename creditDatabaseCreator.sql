CREATE DATABASE IF NOT EXISTS credit;

CREATE TABLE IF NOT EXISTS customer (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	firstName VARCHAR(50) NOT NULL,
	lastName VARCHAR(50) NOT NULL,
	pesel VARCHAR(11) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS customer_sequence
	START 1
	INCREMENT 1
	OWNED BY customer.id
;

CREATE TABLE IF NOT EXISTS credit (
	creditID BIGSERIAL NOT NULL PRIMARY KEY,
	creditName VARCHAR(100) NOT NULL,
	customerID BIGSERIAL REFERENCES customer (id),
	value DOUBLE PRECISION NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS credit_sequence
	START 1
	INCREMENT 1
	OWNED BY credit.creditID
;