CREATE TABLE IF NOT EXISTS "tblUsers"
(
    "id"          BIGSERIAL       NOT NULL PRIMARY KEY,
    "username"    VARCHAR(30)     NOT NULL UNIQUE,
    "password"    CHAR(512)       NOT NULL,
    "email"       VARCHAR(128)    NULL UNIQUE,
    "firstName"   VARCHAR(60)     NOT NULL,
    "lastName"    VARCHAR(60)     NOT NULL,
    "role"        VARCHAR(10)     NOT NULL DEFAULT 'USER',
    "deleted"     BIT(1)          NOT NULL DEFAULT B'0'
)
WITH
(
    OIDS = FALSE
);

ALTER TABLE "tblUsers"
OWNER to postgres;