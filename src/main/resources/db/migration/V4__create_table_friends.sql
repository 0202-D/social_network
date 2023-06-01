create table friends
(
    id             serial primary key,
    first_user_id  int not null,
    second_user_id int not null,
    status         varchar default null
);
alter table friends
    add foreign key (first_user_id) references users;
alter table friends
    add foreign key (second_user_id) references users;
