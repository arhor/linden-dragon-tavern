-- dynamic object extension infrastructure

-- supposed to be used to dynamically add new properties to domain objects
-- additional schema changes is not required, but any extension should be
-- considered as a candidate to the entity schema modification in the future

-- START >>> table 'object_extension'
CREATE TABLE IF NOT EXISTS object_extension
(
    obj_id      BIGINT        NOT NULL,
    obj_table   VARCHAR(63)   NOT NULL,
    field_type  VARCHAR(30)   NOT NULL,
    field_name  VARCHAR(63)   NOT NULL,
    field_value VARCHAR(1024) NULL,

    CONSTRAINT PK_object_extension PRIMARY KEY (obj_id, obj_table, field_name)
) WITH (OIDS = FALSE);
-- END <<< table 'object_extension'
