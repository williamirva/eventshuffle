CREATE TABLE event (
                       id          BIGSERIAL PRIMARY KEY,
                       name        VARCHAR,
                       created_at  TIMESTAMP NOT NULL DEFAULT now()
);