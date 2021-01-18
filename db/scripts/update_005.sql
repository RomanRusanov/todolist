--Add table item_category for link ManyToMany
create table if not exists item_category
(
    item_id integer not null
            references item,
    category_id bigint not null
            references category
);