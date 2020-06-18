CREATE TABLE IF NOT EXISTS authorities
(
    id            BIGSERIAL        NOT NULL PRIMARY KEY,
    name          VARCHAR(100)     NOT NULL UNIQUE
)
WITH
(
    OIDS = FALSE
);

CREATE TABLE IF NOT EXISTS security_profiles
(
    id         BIGSERIAL     NOT NULL PRIMARY KEY,
    name       VARCHAR(50)   NOT NULL UNIQUE,
    created    TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated    TIMESTAMP     NULL,
    deleted    BOOLEAN       NOT NULL DEFAULT FALSE
)
WITH
(
    OIDS = FALSE
);

CREATE TABLE IF NOT EXISTS security_profile_authorities
(
    profile_id      BIGINT    NOT NULL,
    authority_id    BIGINT    NOT NULL,
    CONSTRAINT security_profile_authorities_pk PRIMARY KEY (profile_id, authority_id),
    CONSTRAINT security_profiles_fk FOREIGN KEY (profile_id) REFERENCES security_profiles (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT authorities_fk FOREIGN KEY (authority_id) REFERENCES authorities (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
WITH
(
    OIDS = FALSE
);


CREATE TABLE IF NOT EXISTS accounts
(
    id            BIGSERIAL        NOT NULL PRIMARY KEY,
    username      VARCHAR(30)      NOT NULL UNIQUE,
    password      VARCHAR(1024)    NOT NULL,
    email         VARCHAR(128)     NULL UNIQUE,
    first_name    VARCHAR(60)      NOT NULL,
    last_name     VARCHAR(60)      NOT NULL,
    profile_id    BIGINT           NULL,
    created       TIMESTAMP        NOT NULL DEFAULT NOW(),
    updated       TIMESTAMP        NULL,
    deleted       BOOLEAN          NOT NULL DEFAULT FALSE,
    CONSTRAINT security_profiles_fk FOREIGN KEY (profile_id) REFERENCES security_profiles (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
)
WITH
(
    OIDS = FALSE
);
