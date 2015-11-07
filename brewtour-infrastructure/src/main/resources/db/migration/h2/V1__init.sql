CREATE SCHEMA brewtour;
CREATE TABLE brewtour.location (
  id varchar(32) NOT NULL,
  version integer NOT NULL,
  data clob
);
CREATE TABLE brewtour.locale (
  id varchar(32) NOT NULL,
  version integer NOT NULL,
  data clob
);