package com.media.notifier.facebook.impl.domain;

import com.media.notifier.common.alarm.dto.AlarmType;
import com.media.notifier.common.alarm.dto.NotificationInfo;
import com.media.notifier.facebook.api.FacebookAirAlarmNotifier;
import com.media.notifier.facebook.impl.integration.db.entity.FacebookNotificationEntity;
import com.media.notifier.facebook.impl.integration.db.entity.NotificationStatus;
import com.media.notifier.facebook.impl.integration.db.repository.FacebookNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacebookAirAlarmNotifierImpl implements FacebookAirAlarmNotifier {
    private final FacebookNotificationRepository facebookNotificationRepository;

    @Override
    public Long createNotification(AlarmType type) {
        var facebookNotification = new FacebookNotificationEntity();
        facebookNotification.setType(type);
        facebookNotification.setStatus(NotificationStatus.WAITING_FOR_DELIVERY);
        return facebookNotificationRepository.save(facebookNotification).getId();
    }

    @Override
    public void cancelAllNotification() {
        facebookNotificationRepository.deleteAll();
    }

    @Override
    public NotificationInfo checkNotification(Long notificationId) {
        var telegramNotification = facebookNotificationRepository.findByIdMandatory(notificationId);

        var sent = telegramNotification.getStatus() == NotificationStatus.DELIVERED;
        return NotificationInfo.builder()
                .sent(sent)
                .deliveredAt(telegramNotification.getDeliveredAt())
                .build();
    }
}
