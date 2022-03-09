package com.media.notifier.air.alarm.impl.domain.model;

import com.media.notifier.air.alarm.impl.integration.db.entity.AirAlarmStatus;
import com.media.notifier.air.alarm.impl.integration.db.entity.MessageStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AirAlarmInfo {
    AirAlarmStatus status;
    LocalDateTime alarmChangedAt;
    private List<MessageInfo> message;

    public boolean isAllMessagesProcessed() {
        return message.stream()
                .allMatch(m -> m.getStatus() != MessageStatus.WAITING_FOR_DELIVERY);
    }
}
