package com.media.notifier.facebook.impl.domain;

import com.example.util.time.Time;
import com.media.notifier.common.alarm.dto.AlarmType;
import com.media.notifier.facebook.impl.integration.db.entity.FacebookNotificationEntity;
import com.media.notifier.facebook.impl.integration.db.entity.NotificationStatus;
import com.media.notifier.facebook.impl.integration.db.repository.FacebookNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacebookMessageSender {
    private final FacebookNotificationRepository facebookNotificationRepository;
    private final FacebookSender facebookBotSender;
    private final FacebookNotificationTerminateService facebookNotificationTerminateService;


    private static final String AIR_ALARM_START = "❗❗❗Повітряна тривога❗❗❗" +
            "Всім спуститися в укриття❗❗❗" +
            "Час: ";
    private static final String AIR_ALARM_STOP = "⚠️Відбій повітряної тривоги⚠️" +
            "Час: ";

    @Transactional
    public void sendMessage(FacebookNotificationEntity entity) {
        var facebookNotificationEntity = facebookNotificationRepository.findByIdMandatory(entity.getId());

        var message = getMessage(entity);
        var messageWithDate = addDateTime(message);
        try {
            log.info("Sending facebook message: " + messageWithDate);
            facebookBotSender.sendMessage(messageWithDate);
        } catch (Exception e) {
            facebookNotificationTerminateService.terminateNotification(facebookNotificationEntity.getId());
            throw e;
        }

        facebookNotificationEntity.setStatus(NotificationStatus.DELIVERED);
        facebookNotificationEntity.setDeliveredAt(Time.currentDateTime());

        facebookNotificationRepository.save(facebookNotificationEntity);
    }

    private String addDateTime(String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return message + Time.currentDateTime().format(formatter);
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
