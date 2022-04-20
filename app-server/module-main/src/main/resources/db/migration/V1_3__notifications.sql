-- notifications infrastructure
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- START >>> table 'notifications'
CREATE TABLE IF NOT EXISTS notifications
(
    id         UUID         NOT NULL PRIMARY KEY DEFAULT uuid_generate_v4(),
    type       VARCHAR(10)  NOT NULL,
    message    VARCHAR(500) NOT NULL,
    timestamp  TIMESTAMP    NOT NULL,
    account_id BIGINT       NOT NULL,

    CONSTRAINT FK_notifications_accounts FOREIGN KEY (account_id)
        REFERENCES accounts (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) WITH (OIDS = FALSE);
-- END <<< table 'notifications'
