package com.media.notifier.air.alarm.impl.domain.service;

import com.media.notifier.air.alarm.impl.domain.model.AirAlarmStatus;
import com.media.notifier.air.alarm.impl.integration.db.repository.AirAlarmRepository;
import com.media.notifier.telegram.api.TelegramAirAlarmNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AirAlarmService {
    private final AirAlarmRepository airAlarmRepository;
    private final TelegramAirAlarmNotifier telegramAirAlarmNotifier;

    @Transactional
    public void startAirAlarm() {
        var airAlarm = airAlarmRepository.findAirAlarm();
        var telegramNotificationId = telegramAirAlarmNotifier.createNotification();


    }

    public void stopAirAlarm() {

    }

    public AirAlarmStatus getAlarmStatus() {
        return null;
    }
}
