package com.media.notifier.air.alarm.impl.web;

import com.media.notifier.air.alarm.impl.integration.db.entity.MessageEntity;
import com.media.notifier.air.alarm.impl.integration.db.entity.MessageStatus;
import com.media.notifier.air.alarm.impl.integration.db.repository.DestinationRepository;
import com.media.notifier.air.alarm.impl.integration.db.repository.MessageRepository;
import com.media.notifier.air.alarm.impl.web.dto.TestMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/messages")
@ConditionalOnProperty(
        value = "test.message.controller.enabled",
        havingValue = "true")
@RequiredArgsConstructor
public class TestMessageController {
    private final MessageRepository messageRepository;
    private final DestinationRepository destinationRepository;

    @PostMapping(value = "/test")
    public void sendTestMessage(@RequestBody TestMessage testMessage) {
        var destination = destinationRepository.findByIdMandatory(testMessage.getDestinationId());
        var message = new MessageEntity();
        message.setDestination(destination);
        message.setStatus(MessageStatus.WAITING_FOR_DELIVERY);
        message.setMessage(testMessage.getMessage());
        messageRepository.save(message);
    }
}
