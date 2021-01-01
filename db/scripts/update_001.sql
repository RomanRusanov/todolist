--Create table item
create table item(
                     item_id SERIAL PRIMARY KEY,
                     description VARCHAR(255),
                     created TIMESTAMP,
                     done BOOLEAN
);