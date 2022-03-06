package com.media.notifier.telegram.impl.domain;

import com.example.util.time.Time;
import com.media.notifier.common.alarm.dto.AlarmType;
import com.media.notifier.telegram.impl.integration.db.entity.NotificationStatus;
import com.media.notifier.telegram.impl.integration.db.entity.TelegramNotificationEntity;
import com.media.notifier.telegram.impl.integration.db.repository.TelegramNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TelegramMessageSender {
    private final TelegramNotificationRepository telegramNotificationRepository;
    private final TelegramBotSender telegramBotSender;
    private final TelegramNotificationTerminateService telegramNotificationTerminateService;

    private static final String AIR_ALARM_START = "❗❗❗Повітряна тривога❗❗❗Всім спуститися в укриття❗❗❗";
    private static final String AIR_ALARM_STOP = "⚠️Відбій повітряної тривоги⚠️";

    @Transactional
    public void sendMessage(TelegramNotificationEntity entity) {
        var telegramNotificationEntity = telegramNotificationRepository.findByIdMandatory(entity.getId());

        var message = getMessage(entity);
        try {
            telegramBotSender.sendMessage(message);
        } catch (Exception e) {
            telegramNotificationTerminateService.terminateNotification(telegramNotificationEntity.getId());
            throw e;
        }

        telegramNotificationEntity.setStatus(NotificationStatus.DELIVERED);
        telegramNotificationEntity.setDeliveredAt(Time.currentDateTime());

        telegramNotificationRepository.save(telegramNotificationEntity);
    }

    private String getMessage(TelegramNotificationEntity entity) {
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
