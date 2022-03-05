create table telegram_air_alarm
(
    `id`           bigint auto_increment primary key,
    `status`       varchar(255) not null,
    `delivered_at` datetime(6) default null
);

create table facebook_air_alarm
(
    `id`           bigint auto_increment primary key,
    `status`       varchar(255) not null,
    `delivered_at` datetime(6) default null
);

create table air_alarm
(
    `id`                       int primary key,
    `status`                   varchar(255) not null,
    `alarm_started`            datetime(6) default null,
    `telegram_notification_id` bigint default null,
    `facebook_notification_id` bigint default null,
    constraint `fk_air_alarm_telegram_notification_id`
        foreign key (`telegram_notification_id`)
            references `telegram_air_alarm` (`id`)
            on update cascade
            on delete set null,
    constraint `fk_air_alarm_facebook_notification_id`
        foreign key (`facebook_notification_id`)
            references `facebook_air_alarm` (`id`)
            on update cascade
            on delete set null
);
