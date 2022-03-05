package com.media.notifier.telegram.impl.domain;

import com.media.notifier.telegram.impl.integration.db.repository.TelegramNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NotificationDeliveryTimer {
    private final TelegramNotificationRepository telegramNotificationRepository;
    private final TelegramMessageSender telegramMessageSender;

    @Scheduled(
            fixedDelayString = "${telegram.send.notification.delay.between-jobs}",
            initialDelayString = "${telegram.send.notification.delay.initial}"
    )
    public void deliverNotification() {
        telegramNotificationRepository.findNotificationForSend()
                .ifPresent(telegramMessageSender::sendMessage);
    }
}
