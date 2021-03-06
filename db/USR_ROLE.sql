CREATE TABLE USR_ROLE
(
    ID           VARCHAR(16) PRIMARY KEY,
    NAME        VARCHAR(32)  NOT NULL,
    TYPE         CHAR(3)      NOT NULL,
    DESCRIPTION  VARCHAR(100),
    IS_SYSTEM    CHAR(1),
    LANDING_PAGE VARCHAR(512) NOT NULL,
    CREATED_AT   DATETIME     NOT NULL,
    CREATED_BY   VARCHAR(32)  NOT NULL,
    UPDATED_AT   DATETIME     NOT NULL,
    UPDATED_BY   VARCHAR(32)  NOT NULL
);

INSERT INTO USR_ROLE (ID, NAME, TYPE, IS_SYSTEM, LANDING_PAGE, CREATED_AT, CREATED_BY,
                     UPDATED_AT, UPDATED_BY)
VALUES ('GODADMIN', '超级管理员', 'SYS', 'Y', '/web/systemManagement/issueStatus', NOW(), 'SYSTEM', NOW(), 'SYSTEM');

INSERT INTO USR_ROLE (ID, NAME, TYPE, IS_SYSTEM, LANDING_PAGE, CREATED_AT, CREATED_BY,
                     UPDATED_AT, UPDATED_BY)
VALUES ('DEVOPS', '运维', 'SYS', 'N', '/web/issue/view', NOW(), 'SYSTEM', NOW(), 'SYSTEM');

INSERT INTO USR_ROLE (ID, NAME, TYPE, IS_SYSTEM, LANDING_PAGE, CREATED_AT, CREATED_BY,
                     UPDATED_AT, UPDATED_BY)
VALUES ('STUDENT', '学生', 'DEV', 'N', '/web/issue/view', NOW(), 'SYSTEM', NOW(), 'SYSTEM');

INSERT INTO USR_ROLE (ID, NAME, TYPE, IS_SYSTEM, LANDING_PAGE, CREATED_AT, CREATED_BY,
                     UPDATED_AT, UPDATED_BY)
VALUES ('TEACHER', '教职工', 'DEV', 'N', '/web/issue/view', NOW(), 'SYSTEM', NOW(), 'SYSTEM');

INSERT INTO USR_ROLE (ID, NAME, TYPE, IS_SYSTEM, LANDING_PAGE, CREATED_AT, CREATED_BY,
                     UPDATED_AT, UPDATED_BY)
VALUES ('COUNSELLOR', '辅导员', 'ADM', 'N', '/web/issue/view', NOW(), 'SYSTEM', NOW(), 'SYSTEM');

INSERT INTO USR_ROLE (ID, NAME, TYPE, IS_SYSTEM, LANDING_PAGE, CREATED_AT, CREATED_BY,
                     UPDATED_AT, UPDATED_BY)
VALUES ('DIRECTOR', '教务主任', 'ADM', 'N', '/web/issue/view', NOW(), 'SYSTEM', NOW(), 'SYSTEM');

INSERT INTO USR_ROLE (ID, NAME, TYPE, IS_SYSTEM, LANDING_PAGE, CREATED_AT, CREATED_BY,
                     UPDATED_AT, UPDATED_BY)
VALUES ('DEAN', '院系主任', 'USR', 'N', '/web/index', NOW(), 'SYSTEM', NOW(), 'SYSTEM');

