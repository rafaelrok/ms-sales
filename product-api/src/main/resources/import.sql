INSERT INTO tb_category (id, DESCRIPTION) VALUES (1000, 'Comic Books');
INSERT INTO tb_category (id, DESCRIPTION) VALUES (1001, 'Movies');
INSERT INTO tb_category (id, DESCRIPTION) VALUES (1002, 'Books');
INSERT INTO tb_category (id, DESCRIPTION) VALUES (1003, 'Music');
INSERT INTO tb_category (id, DESCRIPTION) VALUES (1004, 'Games');
INSERT INTO tb_category (id, DESCRIPTION) VALUES (1005, 'Software');
INSERT INTO tb_category (id, DESCRIPTION) VALUES (1006, 'Video Games');
INSERT INTO tb_category (id, DESCRIPTION) VALUES (1007, 'TV Shows');
INSERT INTO tb_category (id, DESCRIPTION) VALUES (1008, 'Anime');
INSERT INTO tb_category (id, DESCRIPTION) VALUES (1009, 'Other');

INSERT INTO tb_supplier (ID, NAME) VALUES (1000, 'Panini Comics');
INSERT INTO tb_supplier (ID, NAME) VALUES (1001, 'Amazon');
INSERT INTO tb_supplier (ID, NAME) VALUES (1002, 'Google');
INSERT INTO tb_supplier (ID, NAME) VALUES (1003, 'Microsoft');
INSERT INTO tb_supplier (ID, NAME) VALUES (1004, 'Apple');
INSERT INTO tb_supplier (ID, NAME) VALUES (1006, 'Twitter');
INSERT INTO tb_supplier (ID, NAME) VALUES (1007, 'Instagram');
INSERT INTO tb_supplier (ID, NAME) VALUES (1008, 'Youtube');
INSERT INTO tb_supplier (ID, NAME) VALUES (1009, 'Snapchat');
INSERT INTO tb_supplier (ID, NAME) VALUES (1010, 'Spotify');
INSERT INTO tb_supplier (ID, NAME) VALUES (1011, 'Netflix');
INSERT INTO tb_supplier (ID, NAME) VALUES (1012, 'Hulu');
INSERT INTO tb_supplier (ID, NAME) VALUES (1013, 'Disney');
INSERT INTO tb_supplier (ID, NAME) VALUES (1014, 'Disney+');
INSERT INTO tb_supplier (ID, NAME) VALUES (1015, 'Disney Junior');

INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1001, 'Crise nas Infinitas Terras', 1000, 1000, 10, CURRENT_TIMESTAMP),
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1002, 'Interestelar', 1001, 1001, 5, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1003, 'Harry Potter E A Pedra Filosofal', 1001, 1002, 3, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1004, 'O Senhor dos Anéis a sociedade do anel', 1001, 1002, 2, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1005, 'O Hobbit uma jornada inesperada', 1001, 1002, 1, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1006, 'O Senhor dos Anéis as duas torres', 1001, 1002, 1, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1007, 'O Hobbit a desolação de smalgu', 1001, 1002, 1, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1008, 'O Senhor dos Anéis o retorno do rei', 1001, 1002, 1, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1009, 'O Hobbit a batalha dos 5 exercitos', 1001, 1002, 1, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1010, 'Harry Potter e a padra Filosofal', 1001, 1002, 1, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1011, 'Harry Potter a camara secreta', 1001, 1002, 1, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1012, 'Harry Potter o prisoneiro de askabam', 1001, 1002, 1, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1013, 'Harry Potter e o calice de fogo', 1001, 1002, 1, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1014, 'Harry Potter a ordem da fenix', 1001, 1002, 1, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1015, 'Harry Potter O eniguima do principe', 1001, 1002, 1, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1016, 'Harry Potter As reliquias da morte parte 1', 1001, 1002, 1, CURRENT_TIMESTAMP);
INSERT INTO tb_product (ID, NAME, FK_SUPPLIER, FK_CATEGORY, QUANTITY_AVAILABLE, CREATED_AT) VALUES (1017, 'Harry Potter As reliquias da morte parte 2', 1001, 1002, 1, CURRENT_TIMESTAMP);

