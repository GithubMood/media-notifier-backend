package com.media.notifier.telegram.impl.domain;

import com.media.notifier.telegram.impl.integration.db.entity.NotificationStatus;
import com.media.notifier.telegram.impl.integration.db.repository.TelegramNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramNotificationTerminateService {
    private final TelegramNotificationRepository telegramNotificationRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void terminateNotification(Long entityId){
        var entity = telegramNotificationRepository.findByIdMandatory(entityId);
        entity.setStatus(NotificationStatus.FAILED);
        telegramNotificationRepository.save(entity);
    }
}
