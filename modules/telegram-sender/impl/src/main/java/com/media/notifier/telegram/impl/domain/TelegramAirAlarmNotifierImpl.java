package com.media.notifier.telegram.impl.domain;

import com.media.notifier.common.alarm.dto.AlarmInfo;
import com.media.notifier.telegram.api.TelegramAirAlarmNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramAirAlarmNotifierImpl implements TelegramAirAlarmNotifier {
    @Override
    public Long createNotification() {
        return null;
    }

    @Override
    public AlarmInfo checkNotification(Long notificationId) {
        return null;
    }
}
