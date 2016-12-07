CREATE SCHEMA IF NOT EXISTS unit10db;

CREATE SEQUENCE unit10db.user_seq;
/*DO
$$
BEGIN
  CREATE SEQUENCE unit10db.user_seq;
  EXCEPTION WHEN duplicate_table THEN
  -- do nothing, it's already there
END
$$ LANGUAGE plpgsql;*/

CREATE TABLE IF NOT EXISTS unit10db.user (
  id           INTEGER PRIMARY KEY,
  login        VARCHAR(255) NOT NULL,
  firstName    VARCHAR(255) NOT NULL,
  lastName     VARCHAR(255) NOT NULL,
  email        VARCHAR(255) NOT NULL,
  passwordHash VARCHAR(255) NOT NULL
);


/*DO
$$
BEGIN
  CREATE SEQUENCE unit10db.role_seq;
  EXCEPTION WHEN duplicate_table THEN
  -- do nothing, it's already there
END
$$ LANGUAGE plpgsql;*/
CREATE SEQUENCE unit10db.role_seq;

CREATE TABLE IF NOT EXISTS unit10db.role (
  id      INTEGER PRIMARY KEY,
  name    VARCHAR(255) NOT NULL,
  user_id INTEGER      NOT NULL,
  FOREIGN KEY (user_id) REFERENCES unit10db.user (id)
);