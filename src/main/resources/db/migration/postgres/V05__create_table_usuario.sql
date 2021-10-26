create table usuario (
    id serial not null primary key,
    username varchar(100) not null unique,
    password varchar(10) not null
);