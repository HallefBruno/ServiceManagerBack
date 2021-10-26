create table cliente (
    id serial not null primary key,
    nome varchar(100) not null,
    cpf varchar(11) not null unique,
    data_nascimento date not null
);