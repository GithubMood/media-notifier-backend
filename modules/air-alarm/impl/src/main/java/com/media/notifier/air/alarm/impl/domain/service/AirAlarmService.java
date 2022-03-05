package com.media.notifier.air.alarm.impl.domain.service;

import com.example.util.time.Time;
import com.media.notifier.air.alarm.impl.domain.model.AirAlarmInfo;
import com.media.notifier.air.alarm.impl.integration.db.entity.AirAlarmStatus;
import com.media.notifier.air.alarm.impl.integration.db.repository.AirAlarmRepository;
import com.media.notifier.telegram.api.TelegramAirAlarmNotifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.media.notifier.air.alarm.impl.integration.db.entity.AirAlarmStatus.STARTED;
import static com.media.notifier.air.alarm.impl.integration.db.entity.AirAlarmStatus.STOPPED;

@Service
@RequiredArgsConstructor
public class AirAlarmService {
    private final AirAlarmRepository airAlarmRepository;
    private final TelegramAirAlarmNotifier telegramAirAlarmNotifier;

    @Transactional
    public void startAirAlarm() {
        var airAlarm = airAlarmRepository.findAirAlarm();

        var telegramNotificationId = telegramAirAlarmNotifier.createNotification();

        airAlarm.setStatus(STARTED);
        airAlarm.setAlarmStarted(Time.currentDateTime());
        airAlarm.setTelegramNotificationId(telegramNotificationId);

        airAlarmRepository.save(airAlarm);
    }

    @Transactional
    public void stopAirAlarm() {
        var airAlarm = airAlarmRepository.findAirAlarm();
        airAlarm.setStatus(STOPPED);
        telegramAirAlarmNotifier.cancelAllNotification();
        airAlarmRepository.save(airAlarm);
    }

    public AirAlarmInfo getAlarmInfo() {
        var airAlarm = airAlarmRepository.findAirAlarm();
        var status = convertStatus(airAlarm.getStatus());
        var alarmInfo = AirAlarmInfo.builder()
                .status(status)
                .build();

        if (status == AirAlarmInfo.Status.STARTED) {
            alarmInfo.setAlarmStarted(airAlarm.getAlarmStarted());

            var telegramNotificationId = airAlarm.getTelegramNotificationId();
            populateTelegramInfo(alarmInfo, telegramNotificationId);

//            var facebookNotificationId = airAlarm.getFacebookNotificationId();
//            populateFacebookInfo(alarmInfo, facebookNotificationId);
        }

        return alarmInfo;
    }

    private void populateTelegramInfo(AirAlarmInfo alarmInfo, Long telegramNotificationId) {
        var telegramInfo = telegramAirAlarmNotifier.checkNotification(telegramNotificationId);

        alarmInfo.setTelegramPublished(telegramInfo.isSent());
        alarmInfo.setTelegramPublishedAt(telegramInfo.getDeliveredAt());
    }

    private void populateFacebookInfo(AirAlarmInfo alarmInfo, Long facebookNotificationId) {
        var facebookInfo = telegramAirAlarmNotifier.checkNotification(facebookNotificationId);

        alarmInfo.setFacebookPublished(facebookInfo.isSent());
        alarmInfo.setFacebookPublishedAt(facebookInfo.getDeliveredAt());
    }

    private AirAlarmInfo.Status convertStatus(AirAlarmStatus status) {
        if (status == STARTED) {
            return AirAlarmInfo.Status.STARTED;
        }
        if (status == STOPPED) {
            return AirAlarmInfo.Status.STOPPED;
        }

        throw new IllegalStateException("Unknown status: " + status);
    }
}
