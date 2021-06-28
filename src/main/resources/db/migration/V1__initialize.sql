drop table if exists party_host_tbl cascade;
create table party_host_tbl
(
    party_host_id   bigserial,
    firstname_fld   varchar(255)   not null,
    lastname_fld    varchar(255)   not null,
    description_fld varchar(500)   not null,
    price_fld       decimal(10, 2) not null,
    primary key (party_host_id)
);
insert into party_host_tbl (firstname_fld, lastname_fld, description_fld, price_fld)
values ('Ivan', 'Ivanov', 'some descr', 1000),
       ('Petr', 'Petrov', 'some descr', 2000),
       ('Maria', 'Sidorova', 'some descr', 3000)
;


drop table if exists party_type_tbl cascade;
create table party_type_tbl
(
    party_type_id bigserial,
    title_fld     varchar(255) not null unique,
    primary key (party_type_id)
);
insert into party_type_tbl (title_fld)
values ('New Year'),
       ('Birthday')
;


drop table if exists audience_type_tbl cascade;
create table audience_type_tbl
(
    audience_type_id bigserial,
    title_fld        varchar(255) not null unique,
    primary key (audience_type_id)
);
insert into audience_type_tbl (title_fld)
values ('Children'),
       ('Adults')
;


drop table if exists party_host_paty_type_m2m_tbl cascade;
create table party_host_paty_type_m2m_tbl
(
    party_host_id bigint not null,
    party_type_id bigint not null,
    primary key (party_host_id, party_type_id),
    foreign key (party_host_id) references party_host_tbl (party_host_id),
    foreign key (party_type_id) references party_type_tbl (party_type_id)
);
insert into party_host_paty_type_m2m_tbl (party_host_id, party_type_id)
values (1, 2),
       (2, 1),
       (3, 1),
       (3, 2)
;


drop table if exists party_host_audience_type_m2m_tbl cascade;
create table party_host_audience_type_m2m_tbl
(
    party_host_id    bigint not null,
    audience_type_id bigint not null,
    primary key (party_host_id, audience_type_id),
    foreign key (party_host_id) references party_host_tbl (party_host_id),
    foreign key (audience_type_id) references audience_type_tbl (audience_type_id)
);
insert into party_host_audience_type_m2m_tbl (party_host_id, audience_type_id)
values (1, 2),
       (2, 1),
       (3, 1),
       (3, 2)
;


drop table if exists client_tbl cascade;
create table client_tbl
(
    client_id     bigserial,
    firstname_fld varchar(255) not null,
    lastname_fld  varchar(255) not null,
    primary key (client_id)
);
insert into client_tbl (firstname_fld, lastname_fld)
values ('Ivan', 'Petrov'),
       ('Petr', 'Ivanov'),
       ('Olga', 'Sidorova')
;


drop table if exists restaurant_tbl cascade;
create table restaurant_tbl
(
    restaurant_id   bigserial,
    title_fld       varchar(255) not null,
    description_fld varchar(500) not null,
    primary key (restaurant_id)
);
insert into restaurant_tbl (title_fld, description_fld)
values ('Papa Johns', 'some descr'),
       ('Pizza Hut', 'som descr')
;


drop table if exists restaurant_hall_tbl cascade;
create table restaurant_hall_tbl
(
    restaurant_hall_id bigserial,
    place_count_fld    int            not null,
    price_fld          decimal(10, 2) not null,
    restaurant_id      bigint         not null,
    primary key (restaurant_hall_id),
    foreign key (restaurant_id) references restaurant_tbl (restaurant_id)
);
insert into restaurant_hall_tbl (place_count_fld, price_fld, restaurant_id)
values (20, 10000, 1),
       (40, 20000, 1),
       (15, 15000, 2),
       (30, 30000, 2)
;


drop table if exists dish_type_tbl cascade;
create table dish_type_tbl
(
    dish_type_id bigserial,
    title_fld    varchar(255) not null unique,
    primary key (dish_type_id)
);
insert into dish_type_tbl (title_fld)
values ('Drink'),
       ('Pizza')
;


drop table if exists dish_tbl cascade;
create table dish_tbl
(
    dish_id         bigserial,
    title_fld       varchar(255)   not null,
    description_fld varchar(500)   not null,
    price_fld       decimal(10, 2) not null,
    restaurant_id   bigint         not null,
    dish_type_id    bigint         not null,
    primary key (dish_id),
    foreign key (restaurant_id) references restaurant_tbl (restaurant_id),
    foreign key (dish_type_id) references dish_type_tbl (dish_type_id)
);
insert into dish_tbl (title_fld, description_fld, price_fld, restaurant_id, dish_type_id)
values ('Tea', 'some tea', 80, 1, 1),
       ('Coffee', 'some coffee', 100, 2, 1),
       ('Margarita', 'some pizza', 400, 1, 2),
       ('Meat pizza', 'some pizza', 600, 2, 2)
;


drop table if exists order_tbl cascade;
create table order_tbl
(
    order_id           bigserial,
    description_fld    varchar(500)   not null,
    total_cost_fld     decimal(10, 2) not null,
    client_id          bigint         not null,
    party_type_id      bigint         not null,
    audience_type_id   bigint         not null,
    party_host_id      bigint         not null,
    restaurant_id      bigint         not null,
    restaurant_hall_id bigint         not null,
    primary key (order_id),
    foreign key (client_id) references client_tbl (client_id),
    foreign key (party_type_id) references party_type_tbl (party_type_id),
    foreign key (audience_type_id) references audience_type_tbl (audience_type_id),
    foreign key (party_host_id) references party_host_tbl (party_host_id),
    foreign key (restaurant_id) references restaurant_tbl (restaurant_id),
    foreign key (restaurant_hall_id) references restaurant_hall_tbl (restaurant_hall_id)
);

/*insert into order_tbl (description_fld, total_cost_fld, client_id, party_type_id, audience_type_id, party_host_id, restaurant_id, restaurant_hall_id)
values ('some descr', 11000, 1, 1, 1, 3, 1, 1)
;
*/

drop table if exists menu_order_item_tbl cascade;
create table menu_order_item_tbl
(
    menu_order_item_id bigserial,
    count_fld          int            not null,
    total_cost_fld     decimal(10, 2) not null,
    dish_id            bigint         not null,
    order_id           bigint         not null,
    primary key (menu_order_item_id),
    foreign key (dish_id) references dish_tbl (dish_id),
    foreign key (order_id) references order_tbl (order_id)
);