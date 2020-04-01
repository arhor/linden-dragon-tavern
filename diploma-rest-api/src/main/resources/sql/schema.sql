CREATE TABLE IF NOT EXISTS tbl_users
(
    id            BIGSERIAL        NOT NULL PRIMARY KEY,
    username      VARCHAR(30)      NOT NULL UNIQUE,
    password      VARCHAR(1024)    NOT NULL,
    email         VARCHAR(128)     NULL UNIQUE,
    first_name    VARCHAR(60)      NOT NULL,
    last_name     VARCHAR(60)      NOT NULL,
    role          VARCHAR(20)      NOT NULL DEFAULT 'USER',
    created       TIMESTAMP        NOT NULL DEFAULT NOW(),
    updated       TIMESTAMP        NOT NULL DEFAULT NOW(),
    deleted       BIT(1)           NOT NULL DEFAULT B'0'
)
WITH
(
    OIDS = FALSE
);

ALTER TABLE tbl_users
OWNER to postgres;