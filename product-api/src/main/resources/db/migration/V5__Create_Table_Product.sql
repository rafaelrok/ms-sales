CREATE TABLE IF NOT EXISTS tb_product (
    id                 integer      not null,
    name               varchar(255) not null,
    fk_category        integer      not null,
    fk_supplier        integer      not null,
    quantity_available integer      not null,
    created_at         timestamp    not null,
    primary key (id),
    constraint fka0q6ep7sf6yhaypos1nmwtvj3
        foreign key (fk_category) references public.tb_category,
    constraint fksbkchcgbykekohv1w375jarap
        foreign key (fk_supplier) references public.tb_supplier
);


