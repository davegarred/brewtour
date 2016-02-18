CREATE SCHEMA brewtour;

CREATE SEQUENCE locationSequence START WITH 100001; -- INCREMENT BY 10;
CREATE SEQUENCE brewerySequence START WITH 100001; -- INCREMENT BY 10;
CREATE SEQUENCE beerSequence START WITH 100001; -- INCREMENT BY 10;

CREATE TABLE brewtour.user_details_view (
  id varchar(36) NOT NULL,
  version integer NOT NULL,
  data clob
);
CREATE TABLE brewtour.user_auth_view (
  id varchar(36) NOT NULL,
  login varchar(36) NOT NULL,
  version integer NOT NULL,
  data clob
);

CREATE TABLE brewtour.location_view (
  id varchar(36) NOT NULL,
  version integer NOT NULL,
  data clob
);
CREATE TABLE brewtour.beer_view (
  id varchar(36) NOT NULL,
  version integer NOT NULL,
  data clob
);
CREATE TABLE brewtour.locale_view (
  id varchar(36) NOT NULL,
  version integer NOT NULL,
  data clob
);
CREATE TABLE brewtour.admin_view (
  id varchar(36) NOT NULL,
  version integer NOT NULL,
  data clob
);

CREATE TABLE brewtour.event_store (
  agg_type varchar(36) NOT NULL,
  id varchar(48) NOT NULL,
  sequence_number BIGINT NOT NULL,
  payload_type varchar(128) NOT NULL,
  payload CLOB,
  metadata CLOB
);
