package com.media.notifier.common.alarm;

import com.media.notifier.common.alarm.dto.AlarmType;
import com.media.notifier.common.alarm.dto.NotificationInfo;

public interface AirAlarmNotifier {
    Long createNotification(AlarmType type);

    void cancelAllNotification();

    NotificationInfo checkNotification(Long notificationId);
}
