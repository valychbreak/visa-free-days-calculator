
create sequence "user_id_seq"
    start 1
    increment 1
    minvalue 1
    cache 1;

create table "user"
(
    id bigint not null default nextval('user_id_seq'::regclass),
    username varchar(32) not null unique,
    password varchar(32) not null,
    email varchar(32) not null unique,
    is_temporary boolean not null default false,

    constraint user_pkey primary key (id)
);

create sequence "travel_period_id_seq"
    start 1
    increment 1
    minvalue 1
    cache 1;

create table "travel_period"
(
    id bigint not null default nextval('travel_period_id_seq'::regclass),
    start date not null,
    "end" date not null,
    country varchar(32) not null,
    note varchar(255) not null,
    user_id bigint not null,

    constraint travel_period_pkey primary key (id),
    constraint user_id_fk foreign key (user_id)
        references "user" (id) match simple
);