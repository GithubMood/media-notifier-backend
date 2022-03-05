package com.media.notifier.air.alarm.impl.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AirAlarmInfo {
    Status status;
    LocalDateTime alarmStarted;

    Boolean facebookPublished;
    LocalDateTime facebookPublishedAt;

    Boolean telegramPublished;
    LocalDateTime telegramPublishedAt;

    public enum Status {
        STARTED,
        STOPPED,
    }
}
