package com.media.notifier.telegram.impl.domain;

import com.example.util.time.Time;
import com.media.notifier.telegram.impl.integration.db.entity.NotificationStatus;
import com.media.notifier.telegram.impl.integration.db.entity.TelegramNotificationEntity;
import com.media.notifier.telegram.impl.integration.db.repository.TelegramNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TelegramMessageSender {
    @Autowired
    private TelegramNotificationRepository telegramNotificationRepository;

    @Transactional
    public void sendMessage(TelegramNotificationEntity entity) {
        entity.setStatus(NotificationStatus.DELIVERED);
        entity.setDeliveredAt(Time.currentDateTime());
        telegramNotificationRepository.save(entity);
    }
}
