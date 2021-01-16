-- audit infrastructure

-- START >>> table 'audit_meta_data'
CREATE TABLE IF NOT EXISTS audit_meta_data
(
    id          BIGSERIAL   NOT NULL PRIMARY KEY,
    entity_name VARCHAR(50) NOT NULL UNIQUE,
    table_name  VARCHAR(63) NOT NULL,
    created     TIMESTAMP   NOT NULL DEFAULT NOW()
) WITH (OIDS = FALSE);
-- END <<< table 'audit_meta_data'

