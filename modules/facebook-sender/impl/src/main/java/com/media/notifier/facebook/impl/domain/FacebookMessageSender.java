package com.media.notifier.facebook.impl.domain;

import com.example.util.time.Time;
import com.media.notifier.common.alarm.dto.AlarmType;
import com.media.notifier.facebook.impl.integration.db.entity.FacebookNotificationEntity;
import com.media.notifier.facebook.impl.integration.db.entity.NotificationStatus;
import com.media.notifier.facebook.impl.integration.db.repository.FacebookNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FacebookMessageSender {
    private final FacebookNotificationRepository facebookNotificationRepository;
    private final FacebookBotSender facebookBotSender;

    private static final String AIR_ALARM_START = "❗❗❗Повітряна тривога❗❗❗Всім спуститися в укриття❗❗❗";
    private static final String AIR_ALARM_STOP = "⚠️Відбій повітряної тривоги⚠️";

    @Transactional
    public void sendMessage(FacebookNotificationEntity entity) {
        var facebookNotificationEntity = facebookNotificationRepository.findByIdMandatory(entity.getId());

        var message = getMessage(entity);
        facebookBotSender.sendMessage(message);

        facebookNotificationEntity.setStatus(NotificationStatus.DELIVERED);
        facebookNotificationEntity.setDeliveredAt(Time.currentDateTime());

        facebookNotificationRepository.save(facebookNotificationEntity);
    }

    private String getMessage(FacebookNotificationEntity entity) {
        var type = entity.getType();

        if (type == AlarmType.START) {
            return AIR_ALARM_START;
        }

        if (type == AlarmType.STOP) {
            return AIR_ALARM_STOP;
        }

        throw new IllegalStateException("Unknown entity type: " + type);
    }
}
