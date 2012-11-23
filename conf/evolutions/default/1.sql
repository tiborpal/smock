# Tasks schema
 
# --- !Ups

CREATE SEQUENCE response_id_seq;
CREATE TABLE response (
    id integer NOT NULL DEFAULT nextval('response_id_seq'),
    requestUri varchar(1000),
    response varchar(4000)
);
 
# --- !Downs
 
DROP TABLE response;
DROP SEQUENCE response_id_seq;
