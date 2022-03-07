package com.media.notifier.telegram.impl.domain;

import com.example.util.time.Time;
import com.media.notifier.common.alarm.dto.AlarmType;
import com.media.notifier.common.utils.ClassPathReader;
import com.media.notifier.telegram.impl.integration.db.entity.NotificationStatus;
import com.media.notifier.telegram.impl.integration.db.entity.TelegramNotificationEntity;
import com.media.notifier.telegram.impl.integration.db.repository.TelegramNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.nio.charset.Charset;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramMessageSender {
    private final TelegramNotificationRepository telegramNotificationRepository;
    private final TelegramBotSender telegramBotSender;
    private final TelegramNotificationTerminateService telegramNotificationTerminateService;

    private static final String AIR_ALARM_START = ClassPathReader.readFile("messages/telegram_start_alarm.txt");
    private static final String AIR_ALARM_STOP = ClassPathReader.readFile("messages/telegram_stop_alarm.txt");

    @PostConstruct
    public void init() {
        log.info("Default charset: " + Charset.defaultCharset().displayName());
        log.info("Test: ❗❗❗Повітряна тривога❗❗❗Всім спуститися в укриття❗❗❗");
    }

    @Transactional
    public void sendMessage(TelegramNotificationEntity entity) {
        log.info("Found telegram notification for sending");
        var telegramNotificationEntity = telegramNotificationRepository.findByIdMandatory(entity.getId());

        var message = getMessage(entity);
        try {
            log.info("Sending telegram message: " + message);
            telegramBotSender.sendMessage(message);
        } catch (Exception e) {
            telegramNotificationTerminateService.terminateNotification(telegramNotificationEntity.getId());
            throw e;
        }

        telegramNotificationEntity.setStatus(NotificationStatus.DELIVERED);
        telegramNotificationEntity.setDeliveredAt(Time.currentDateTime());

        telegramNotificationRepository.save(telegramNotificationEntity);
        log.info("Telegram notification successfully sent");
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
