CREATE SCHEMA brewtour;
CREATE TABLE brewtour.skeleton (
  id varchar(32) NOT NULL,
  version integer NOT NULL,
  data clob
);