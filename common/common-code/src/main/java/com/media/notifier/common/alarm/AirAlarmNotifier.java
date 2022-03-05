package com.media.notifier.common.alarm;

import com.media.notifier.common.alarm.dto.AlarmInfo;

public interface AirAlarmNotifier {
    Long createNotification();

    AlarmInfo checkNotification(Long notificationId);
}
