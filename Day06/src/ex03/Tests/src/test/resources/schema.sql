drop table if exists products;

create table products (
                          id bigint identity primary key,
                          name varchar(256),
                          price int
);