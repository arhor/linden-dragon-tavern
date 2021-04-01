-- basic infrastructure for the authentication/authorization process
-- consists of accounts, security profiles and authorities
-- each account must be assigned to the security profile which has several authorities
-- each authority describes resource access rights, like `ACCOUNT:VIEW` or `ACCOUNT:EDIT`

-- START >>> table 'authorities'
CREATE TABLE IF NOT EXISTS authorities
(
    id   BIGSERIAL    NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
)
    WITH (OIDS = FALSE);
-- END <<< table 'authorities'

-- START >>> table 'security_profiles'
CREATE TABLE IF NOT EXISTS security_profiles
(
    id        BIGSERIAL   NOT NULL PRIMARY KEY,
    name      VARCHAR(50) NOT NULL UNIQUE,
    created   TIMESTAMP   NOT NULL,
    updated   TIMESTAMP   NULL,
    deleted   BOOLEAN     NOT NULL DEFAULT FALSE,
    synthetic BOOLEAN     NOT NULL DEFAULT FALSE
)
    WITH (OIDS = FALSE);
-- END <<< table 'security_profiles'

-- START >>> table 'security_profile_authorities'
CREATE TABLE IF NOT EXISTS security_profile_authorities
(
    profile_id   BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,

    CONSTRAINT PK_security_profile_authorities PRIMARY KEY (profile_id, authority_id),

    CONSTRAINT FK_security_profile_authorities_security_profiles FOREIGN KEY (profile_id)
        REFERENCES security_profiles (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT FK_security_profile_authorities_authorities FOREIGN KEY (authority_id)
        REFERENCES authorities (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
)
    WITH (OIDS = FALSE);
-- END <<< table 'security_profile_authorities'

-- START >>> table 'accounts'
CREATE TABLE IF NOT EXISTS accounts
(
    id         BIGSERIAL     NOT NULL PRIMARY KEY,
    username   VARCHAR(30)   NOT NULL UNIQUE,
    password   VARCHAR(1024) NOT NULL,
    email      VARCHAR(128)  NULL UNIQUE,
    created    TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated    TIMESTAMP     NULL     DEFAULT NULL,
    deleted    BOOLEAN       NOT NULL DEFAULT FALSE
)
    WITH (OIDS = FALSE);
-- END <<< table 'accounts'

-- START >>> table 'account_details'
CREATE TABLE IF NOT EXISTS account_details
(
    id         BIGSERIAL     NOT NULL PRIMARY KEY,
    username   VARCHAR(30)   NOT NULL UNIQUE,
    first_name VARCHAR(60)   NOT NULL,
    last_name  VARCHAR(60)   NOT NULL,
    profile_id BIGINT        NULL,
    created    TIMESTAMP     NOT NULL DEFAULT NOW(),
    updated    TIMESTAMP     NULL     DEFAULT NULL,
    deleted    BOOLEAN       NOT NULL DEFAULT FALSE,

    CONSTRAINT FK_accounts_security_profiles FOREIGN KEY (profile_id)
        REFERENCES security_profiles (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
)
    WITH (OIDS = FALSE);
-- END <<< table 'accounts'
