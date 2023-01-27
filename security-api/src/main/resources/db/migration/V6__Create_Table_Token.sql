CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE token_validation
(
    id          UUID                        NOT NULL,
    token       VARCHAR(255)                NOT NULL,
    user_id     UUID,
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_token_validation PRIMARY KEY (id)
);

ALTER TABLE token_validation
    ADD CONSTRAINT FK_TOKEN_VALIDATION_ON_USER FOREIGN KEY (user_id) REFERENCES tb_user (id);