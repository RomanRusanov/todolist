create table j_role (
                        id serial primary key,
                        name varchar(2000)
);
create table j_user (
                        id serial primary key,
                        name varchar(2000),
                        role_id int not null references j_role(id)
);
create table j_role_j_user
(
    role_id  integer not null
        constraint fkd0x2yn3hbl6hdhop7ddxy53lv
            references j_role,
    users_id integer not null
        constraint uk_eynvk8o4fq3ouuclffj2an8i5
            unique
        constraint fk8u2html5ih2fk6n6fnjteyvdi
            references j_user
);