create table users
(
    id       serial primary key,
    login    varchar,
    password varchar,
    email    varchar
);
create table roles
(
    id          serial primary key,
    name        varchar,
    description varchar
);
insert into roles (name)
values ('ROLE_ADMINISTRATOR'),
       ('ROLE_GUEST');
create table tokens
(
    id            serial primary key,
    user_id       int,
    refresh_token varchar,
    disabled      boolean,
    create_date   timestamp,
    deleted       boolean
);