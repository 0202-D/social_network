create table posts
(
    id      serial primary key,
    text    varchar,
    header  varchar,
    content  bytea,
    user_id int not null references users (id)
)