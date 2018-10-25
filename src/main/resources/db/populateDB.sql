DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq  RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2018-10-24 10:00:00', 'Завтрак', 140, 100000),
       ('2018-10-24 14:00:00', 'Обед', 500, 100000),
       ('2018-10-24 19:00:00', 'Ужин', 700, 100000),
       ('2018-10-25 10:00:00', 'Завтрак', 100, 100001),
       ('2018-10-25 14:00:00', 'Обед', 500, 100001),
       ('2018-10-25 19:00:00', 'Ужин', 502, 100001);
