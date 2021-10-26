create table servico_prestado (
    id serial not null,
    descricao varchar(255) not null,
    valor numeric(19,2) not null,
    cliente_id int not null,
    PRIMARY KEY(id),
    CONSTRAINT pessoa_fk FOREIGN KEY(cliente_id) REFERENCES cliente(id)
);