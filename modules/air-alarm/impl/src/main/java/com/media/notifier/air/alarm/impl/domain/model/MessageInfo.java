package com.media.notifier.air.alarm.impl.domain.model;

import com.media.notifier.air.alarm.impl.integration.db.entity.DestinationType;
import com.media.notifier.air.alarm.impl.integration.db.entity.MessageStatus;
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
public class MessageInfo {
    private String destination;
    private DestinationType type;
    private MessageStatus status;
    private LocalDateTime deliveredAt;
}
