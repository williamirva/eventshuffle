CREATE TABLE date (
                       id          BIGSERIAL PRIMARY KEY,
                       eventId     BIGINT,
                       date        DATE,
                       votes       INTEGER
);