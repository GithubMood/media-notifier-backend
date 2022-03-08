create table air_alarm
(
    `id`               int primary key,
    `status`           varchar(255) not null,
    `alarm_changed_at` datetime(6) default null
);

create table destination
(
    `id`                bigint auto_increment primary key,
    `name`              varchar(255) not null,
    `type`              varchar(255) not null,
    `destination_id`    varchar(255) not null,
    `destination_token` varchar(255) not null
);

create table message
(
    `id`             bigint auto_increment primary key,
    `status`         varchar(255) not null,
    `message`        varchar(255) not null,
    `delivered_at`   datetime(6) default null,
    `destination_id` bigint       not null,
    constraint `fk_message_destination_id`
        foreign key (`destination_id`)
            references `destination` (`id`)
            on update cascade
            on delete cascade
);

create table user
(
    `id`       bigint auto_increment primary key,
    `login`    varchar(255) not null,
    `password` varchar(255) not null
);
