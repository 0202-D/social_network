create table user_role
(
    id      serial primary key,
    user_id int,
    role_id int
);
alter table user_role
    add foreign key (user_id) references users;
alter table user_role
    add foreign key (role_id) references roles;
alter table tokens
    add foreign key (user_id) references users;
