create table user
(
    `id`       bigint auto_increment primary key,
    `login`    varchar(255) not null,
    `password` varchar(255) not null
);
