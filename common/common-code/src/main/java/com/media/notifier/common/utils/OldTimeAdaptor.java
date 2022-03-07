package com.media.notifier.common.utils;

import com.example.util.time.Time;

import java.time.LocalDateTime;
import java.util.Date;

public class OldTimeAdaptor {
    public static LocalDateTime toLocalDateTime(Date date) {
        return (date == null) ? null : LocalDateTime.ofInstant(date.toInstant(), Time.ZONE_ID);
    }

    public static Date toDate(LocalDateTime localDatetime) {
        return localDatetime == null ? null : Date.from(localDatetime.atZone(Time.ZONE_ID).toInstant());
    }
}
