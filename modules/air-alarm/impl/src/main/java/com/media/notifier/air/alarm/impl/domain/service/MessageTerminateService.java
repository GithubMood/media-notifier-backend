package com.media.notifier.air.alarm.impl.domain.service;

import com.media.notifier.air.alarm.impl.integration.db.entity.MessageStatus;
import com.media.notifier.air.alarm.impl.integration.db.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageTerminateService {
    private final MessageRepository messageRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void failMessage(Long messageId){
        var message = messageRepository.findByIdMandatory(messageId);
        message.setStatus(MessageStatus.FAILED);
        messageRepository.save(message);
    }
}
