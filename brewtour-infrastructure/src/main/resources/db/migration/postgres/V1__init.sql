CREATE TABLE brewtour.location (
  id character varying(36)[] NOT NULL,
  version integer NOT NULL,
  data text
);
CREATE TABLE brewtour.locale (
  id character varying(36)[] NOT NULL,
  version integer NOT NULL,
  data text
);
CREATE TABLE brewtour.user_details (
  id character varying(36)[] NOT NULL,
  version integer NOT NULL,
  data text
);
CREATE TABLE brewtour.user_auth (
  id character varying(36)[] NOT NULL,
  version integer NOT NULL,
  data text
);
