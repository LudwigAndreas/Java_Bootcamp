DROP TABLE IF EXISTS users;
CREATE TABLE users(
	id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	email TEXT
);
INSERT INTO users(email)
VALUES ('123@gmail.com'),
	('456@gmail.com'),
	('789@gmail.com'),
	('sussy@amog.us'),
	('when.the.imposter@is.sus');