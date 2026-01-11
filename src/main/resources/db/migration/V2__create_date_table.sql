CREATE TABLE eventdate (
                       id          BIGSERIAL PRIMARY KEY,
                       eventId     BIGINT,
                       date        DATE,
                       votes       INTEGER
);