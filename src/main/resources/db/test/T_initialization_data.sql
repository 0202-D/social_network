insert into users (id, login, password,email)
values (13, 'login1', '$2a$10$/qkdAUtfdxMs.V5iil9xNO0Laa1uwdqDlDbIi.9X5I5.ieJ9nxk8G','login1@mail.ru'),
       (14,'login2', '$2a$10$hwGTJx3p2yA0AnP.qxQCgey/w8pKn9YmGLtlV5w76O1n2c0bgDrDq','login2@mail.ru');
insert into user_role (user_id, role_id)
values (13, 2),
       (14, 2);
insert into friends (first_user_id,second_user_id,status)
values (13,14,'NEW');

