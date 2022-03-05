package com.media.notifier.author.impl.domain.model;

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
public class AirAlarmStatus {
    Status status;
    LocalDateTime alarmStarted;
    Boolean facebookPublished;
    Boolean telegramPublished;

    public enum Status {
        STARTED,
        STOPPED,
    }
}
