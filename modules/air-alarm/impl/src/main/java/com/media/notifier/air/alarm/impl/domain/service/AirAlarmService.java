package com.media.notifier.air.alarm.impl.domain.service;

import com.example.util.time.Time;
import com.media.notifier.air.alarm.impl.domain.model.AirAlarmInfo;
import com.media.notifier.air.alarm.impl.domain.model.MessageInfo;
import com.media.notifier.air.alarm.impl.integration.db.entity.AirAlarmEntity;
import com.media.notifier.air.alarm.impl.integration.db.entity.AirAlarmStatus;
import com.media.notifier.air.alarm.impl.integration.db.entity.DestinationType;
import com.media.notifier.air.alarm.impl.integration.db.entity.MessageEntity;
import com.media.notifier.air.alarm.impl.integration.db.entity.MessageStatus;
import com.media.notifier.air.alarm.impl.integration.db.repository.AirAlarmRepository;
import com.media.notifier.air.alarm.impl.integration.db.repository.DestinationRepository;
import com.media.notifier.air.alarm.impl.integration.db.repository.MessageRepository;
import com.media.notifier.common.utils.ClassPathReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.time.format.DateTimeFormatter;

import static com.media.notifier.air.alarm.impl.integration.db.entity.AirAlarmStatus.STARTED;
import static com.media.notifier.air.alarm.impl.integration.db.entity.AirAlarmStatus.STOPPED;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirAlarmService {
    private final AirAlarmRepository airAlarmRepository;
    private final MessageRepository messageRepository;
    private final DestinationRepository destinationRepository;

    private static final String AIR_ALARM_START = ClassPathReader.readFile("messages/start_alarm.txt");
    private static final String AIR_ALARM_STOP = ClassPathReader.readFile("messages/stop_alarm.txt");

    @PostConstruct
    public void init() {
        log.info("Default charset: " + Charset.defaultCharset().displayName());
        log.info("Test: ❗❗❗Повітряна тривога❗❗❗Всім спуститися в укриття❗❗❗");
    }

    @Transactional
    public void startAirAlarm() {
        var airAlarm = airAlarmRepository.findAirAlarm();

        updateAlarm(airAlarm, STARTED);

        messageRepository.deleteAll();
        sendNewMessages(STARTED);
    }

    @Transactional
    public void stopAirAlarm() {
        var airAlarm = airAlarmRepository.findAirAlarm();

        updateAlarm(airAlarm, STOPPED);

        messageRepository.deleteAll();
        sendNewMessages(STOPPED);
    }

    @Transactional(readOnly = true)
    public AirAlarmInfo getAlarmInfo() {
        var airAlarm = airAlarmRepository.findAirAlarm();

        var messages = messageRepository.findAll().stream()
                .map(this::convertToMessageInfo)
                .toList();

        return AirAlarmInfo.builder()
                .status(airAlarm.getStatus())
                .alarmChangedAt(airAlarm.getAlarmChangedAt())
                .message(messages)
                .build();
    }

    private MessageInfo convertToMessageInfo(MessageEntity m) {
        return MessageInfo.builder()
                .destination(m.getDestination().getName())
                .type(m.getDestination().getType())
                .status(m.getStatus())
                .deliveredAt(m.getDeliveredAt())
                .build();
    }

    private void updateAlarm(AirAlarmEntity airAlarm, AirAlarmStatus status) {
        airAlarm.setStatus(status);
        airAlarm.setAlarmChangedAt(Time.currentDateTime());
        airAlarmRepository.save(airAlarm);
    }

    private void sendNewMessages(AirAlarmStatus status) {
        destinationRepository.findAll().forEach(destination -> {
            var message = new MessageEntity();
            message.setStatus(MessageStatus.WAITING_FOR_DELIVERY);
            message.setMessage(getMessage(status, destination.getType()));
            message.setDestination(destination);
            messageRepository.save(message);
        });
    }

    private String getMessage(AirAlarmStatus alarmStatus, DestinationType type) {
        var message = convertToMessage(alarmStatus).trim();

        if (type == DestinationType.FACEBOOK) {
            //each facebook message should be unique
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            return message + "Час: " + Time.currentDateTime().format(formatter);
        }

        return message;
    }

    private String convertToMessage(AirAlarmStatus status) {
        if (status == STARTED) {
            return AIR_ALARM_START;
        }
        if (status == STOPPED) {
            return AIR_ALARM_STOP;
        }

        throw new IllegalStateException("Unknown status: " + status);
    }
}
