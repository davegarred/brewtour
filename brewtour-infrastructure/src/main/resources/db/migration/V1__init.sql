CREATE TABLE users (
  username VARCHAR(25) NOT NULL,
  password VARCHAR(25) NOT NULL,
  PRIMARY KEY(username)
);

INSERT INTO users values ('testuser','testpass');
