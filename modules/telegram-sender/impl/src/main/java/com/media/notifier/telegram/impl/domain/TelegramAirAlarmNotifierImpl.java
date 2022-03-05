package com.media.notifier.telegram.impl.domain;

import com.media.notifier.common.alarm.dto.AlarmType;
import com.media.notifier.common.alarm.dto.NotificationInfo;
import com.media.notifier.telegram.api.TelegramAirAlarmNotifier;
import com.media.notifier.telegram.impl.integration.db.entity.NotificationStatus;
import com.media.notifier.telegram.impl.integration.db.entity.TelegramNotificationEntity;
import com.media.notifier.telegram.impl.integration.db.repository.TelegramNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramAirAlarmNotifierImpl implements TelegramAirAlarmNotifier {
    private final TelegramNotificationRepository telegramNotificationRepository;

    @Override
    public Long createNotification(AlarmType type) {
        var telegramNotification = new TelegramNotificationEntity();
        telegramNotification.setType(type);
        telegramNotification.setStatus(NotificationStatus.WAITING_FOR_DELIVERY);
        return telegramNotificationRepository.save(telegramNotification).getId();
    }

    @Override
    public void cancelAllNotification() {
        telegramNotificationRepository.deleteAll();
    }

    @Override
    public NotificationInfo checkNotification(Long notificationId) {
        var telegramNotification = telegramNotificationRepository.findByIdMandatory(notificationId);

        var sent = telegramNotification.getStatus() == NotificationStatus.DELIVERED;
        return NotificationInfo.builder()
                .sent(sent)
                .deliveredAt(telegramNotification.getDeliveredAt())
                .build();
    }
}
