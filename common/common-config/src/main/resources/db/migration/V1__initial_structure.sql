create table air_alarm
(
    `id`            int primary key,
    `status`        varchar(255) not null,
    `alarm_started` datetime(6) default null
);
