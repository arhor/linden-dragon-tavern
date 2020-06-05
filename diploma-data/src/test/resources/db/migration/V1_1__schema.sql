CREATE TABLE IF NOT EXISTS Account
(
    id            BIGSERIAL        NOT NULL PRIMARY KEY,
    username      VARCHAR(30)      NOT NULL UNIQUE,
    password      VARCHAR(1024)    NOT NULL,
    email         VARCHAR(128)     UNIQUE,
    first_name    VARCHAR(60)      NOT NULL,
    last_name     VARCHAR(60)      NOT NULL,
    role          VARCHAR(20)      DEFAULT 'USER',
    created       TIMESTAMP        DEFAULT NOW(),
    updated       TIMESTAMP        ,
    deleted       BOOLEAN          NOT NULL DEFAULT FALSE
)
WITH (OIDS = FALSE);
