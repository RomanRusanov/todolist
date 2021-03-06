--Create table item
create table item(
                     item_id SERIAL PRIMARY KEY,
                     description VARCHAR(255),
                     created TIMESTAMP,
                     done BOOLEAN
);
create table category
(
    id   serial
        constraint category_pkey
            primary key,
    name varchar(255)
);

create table item_category
(
    item_id     integer not null
        constraint fk5aqgdnwpm5w5jplbckl9fur01
            references item,
    category_id bigint  not null
        constraint fk61wifucqaklslrhrk8bviep5t
            references category
);

INSERT INTO public.category (name) VALUES ('Home');
INSERT INTO public.category (name) VALUES ('Work');
INSERT INTO public.category (name) VALUES ('Personal');