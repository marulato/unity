CREATE TABLE USR_ROLE_ASSIGN
(
    ID          BIGINT PRIMARY KEY AUTO_INCREMENT,
    USER_ID     VARCHAR(32)      NOT NULL,
    ROLE_ID     VARCHAR(8)  NOT NULL,
    APPROVED_BY VARCHAR(16),
    REMARKS     VARCHAR(100),
    CREATED_AT  DATETIME    NOT NULL,
    CREATED_BY  VARCHAR(32) NOT NULL,
    UPDATED_AT  DATETIME    NOT NULL,
    UPDATED_BY  VARCHAR(32) NOT NULL,
    INDEX USR_ROLE_ASSIGN_IDX1 (USER_ID)
);