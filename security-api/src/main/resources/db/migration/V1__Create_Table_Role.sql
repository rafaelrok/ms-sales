CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE tb_role
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_tb_role PRIMARY KEY (id)
);