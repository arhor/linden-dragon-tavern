-- START >>> table 'characters'
CREATE TABLE IF NOT EXISTS characters
(
    id             BIGSERIAL       NOT NULL PRIMARY KEY,
    char_name      VARCHAR(50)     NOT NULL,
    str            SMALLINT        NOT NULL,
    dex            SMALLINT        NOT NULL,
    con            SMALLINT        NOT NULL,
    int            SMALLINT        NOT NULL,
    wis            SMALLINT        NOT NULL,
    cha            SMALLINT        NOT NULL,
    passive_wis    SMALLINT        NOT NULL,
    saves_bits     INT             NOT NULL DEFAULT 0,
    background     VARCHAR(50)     NOT NULL,
    player_name    VARCHAR(50)     NOT NULL,
    race           VARCHAR(50)     NOT NULL,
    alignment      VARCHAR(30)     NOT NULL,
    exp_points     INT             NOT NULL DEFAULT 0,
    armor_class    SMALLINT        NOT NULL,
    initiative     SMALLINT        NOT NULL,
    speed          VARCHAR(50)     NOT NULL,
    skills_bits    INT             NOT NULL DEFAULT 0,
    hp_max         SMALLINT        NOT NULL,
    pers_traits    VARCHAR(512)    NULL,
    ideals         VARCHAR(512)    NULL,
    bonds          VARCHAR(512)    NULL,
    flaws          VARCHAR(512)    NULL
)
WITH (OIDS = FALSE);
-- END <<< table 'characters'

-- START >>> table 'character_classes'
CREATE TABLE IF NOT EXISTS character_classes
(
    id              BIGSERIAL      NOT NULL PRIMARY KEY,
    name            VARCHAR(30)    NOT NULL UNIQUE,
    level           SMALLINT       NOT NULL,
    character_id    BIGINT         NOT NULL,

    CONSTRAINT FK_character_classes_characters FOREIGN KEY (character_id)
        REFERENCES characters (id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
)
WITH (OIDS = FALSE);
-- END <<< table 'character_classes'
