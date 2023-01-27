CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE tb_user
(
    id            UUID                        NOT NULL,
    provider_user_id VARCHAR(255),
    first_name    VARCHAR(255)                NOT NULL,
    last_name     VARCHAR(255)                NOT NULL,
    email         VARCHAR(255)                NOT NULL,
    password      VARCHAR(255)                NOT NULL,
    enabled       BOOLEAN                     NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    modified_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_tb_user PRIMARY KEY (id)
);

CREATE TABLE tb_user_role
(
    role_id BIGINT NOT NULL,
    user_id UUID   NOT NULL,
    CONSTRAINT pk_tb_user_role PRIMARY KEY (role_id, user_id)
);

ALTER TABLE tb_user
    ADD CONSTRAINT uc_tb_user_email UNIQUE (email);

ALTER TABLE tb_user_role
    ADD CONSTRAINT fk_tbuserol_on_role FOREIGN KEY (role_id) REFERENCES tb_role (id);

ALTER TABLE tb_user_role
    ADD CONSTRAINT fk_tbuserol_on_user FOREIGN KEY (user_id) REFERENCES tb_user (id);