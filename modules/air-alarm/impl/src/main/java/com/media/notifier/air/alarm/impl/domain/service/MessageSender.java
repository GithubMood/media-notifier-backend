package com.media.notifier.air.alarm.impl.domain.service;

import com.example.util.time.Time;
import com.media.notifier.air.alarm.impl.integration.db.entity.DestinationType;
import com.media.notifier.air.alarm.impl.integration.db.entity.MessageEntity;
import com.media.notifier.air.alarm.impl.integration.db.entity.MessageStatus;
import com.media.notifier.air.alarm.impl.integration.db.repository.MessageRepository;
import com.media.notifier.facebook.api.FacebookMessageSender;
import com.media.notifier.telegram.api.TelegramMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender {
    private final MessageRepository messageRepository;
    private final MessageTerminateService messageTerminateService;
    private final FacebookMessageSender facebookMessageSender;
    private final TelegramMessageSender telegramMessageSender;

    @Transactional
    public void sendMessage(Long messageId) {
        var messageEntity = messageRepository.findByIdMandatory(messageId);

        try {
            performSending(messageEntity);

            messageEntity.setStatus(MessageStatus.DELIVERED);
            messageEntity.setDeliveredAt(Time.currentDateTime());

            messageRepository.save(messageEntity);
            log.info("Message with id {} was successfully sent", messageId);
        } catch (Exception e) {
            log.error("Unable to send message with id: " + messageId, e);
        }
    }

    private void performSending(MessageEntity messageEntity) {
        var destination = messageEntity.getDestination();
        var destinationType = destination.getType();

        try {
            if (destinationType == DestinationType.FACEBOOK) {
                facebookMessageSender.sendMessage(destination.getDestinationId(),
                        destination.getDestinationToken(),
                        messageEntity.getMessage());
                return;
            }
            if (destinationType == DestinationType.TELEGRAM) {
                telegramMessageSender.sendMessage(destination.getDestinationId(),
                        destination.getDestinationToken(),
                        messageEntity.getMessage());
                return;
            }
        } catch (Exception e) {
            messageTerminateService.failMessage(messageEntity.getId());
            throw e;
        }

        throw new IllegalArgumentException("Unknown destination type: " + destinationType);
    }
}
