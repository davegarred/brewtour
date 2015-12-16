CREATE SCHEMA brewtour;
CREATE TABLE brewtour.location (
  id varchar(36) NOT NULL,
  version integer NOT NULL,
  data clob
);
CREATE TABLE brewtour.locale (
  id varchar(36) NOT NULL,
  version integer NOT NULL,
  data clob
);
CREATE TABLE brewtour.user_details (
  id varchar(36) NOT NULL,
  version integer NOT NULL,
  data clob
);
CREATE TABLE brewtour.user_auth (
  id varchar(36) NOT NULL,
  version integer NOT NULL,
  data clob
);
