package com.media.notifier.common.alarm;

import com.media.notifier.common.alarm.dto.NotificationInfo;

public interface AirAlarmNotifier {
    Long createNotification();

    void cancelAllNotification();

    NotificationInfo checkNotification(Long notificationId);
}
